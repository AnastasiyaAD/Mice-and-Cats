package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.server.game.cats.CatManager;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.GameStatus;
import at.ac.tuwien.foop.server.game.state.Mouse;
import at.ac.tuwien.foop.server.network.ClientManager;
import at.ac.tuwien.foop.server.network.UpdateBroadcaster;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * Manages the overall game state and coordinates actions between clients and the game logic.
 */
public class GameManager {

    private final GameState gameState = new GameState();
    private final UpdateBroadcaster updateBroadcaster = new UpdateBroadcaster();
    private final ConcurrentLinkedQueue<ActionRequestDto> actionQueue = new ConcurrentLinkedQueue<>();
    private final Configuration configuration;
    private final CatManager catManager;

    /**
     * Constructor for GameManager.
     *
     * @param configuration The configuration settings for the game.
     */
    public GameManager(Configuration configuration) {
        this.configuration = configuration;
        this.catManager = new CatManager();
    }

    /**
     * Queues an action request to be processed.
     *
     * @param actionRequestDto The action request from a client.
     */
    public synchronized void queueAction(ActionRequestDto actionRequestDto) {
        actionQueue.add(actionRequestDto);
    }

    /**
     * Registers a new client in the game.
     *
     * @param clientManager The manager for the client being registered.
     */
    public void registerClient(ClientManager clientManager) {
        var newMouse = new Mouse(clientManager.getUsername());
        gameState.addMouse(clientManager.getClientId(), newMouse);
        updateBroadcaster.addClient(clientManager);
        System.out.printf("Registered user %s, with clientId %s%n", clientManager.getUsername(), clientManager.getClientId());
    }

    /**
     * Updates the game state, processing queued actions, and broadcasting updates to clients.
     */
    public void updateGame() {
        if (gameState.getGameStart() != null || allClientsReady()) {
            if (gameState.getGameStart() == null) {
                // Spawn cats and set in game state
                gameState.setCats(catManager.spawnCats(configuration, gameState));
                gameState.setGameStart(LocalDateTime.now());
                gameState.setGameStatus(GameStatus.RUNNING);
            }
            timeCheck();
            processActions();
            catManager.updateCats(gameState);
            checkState();
            updateBroadcaster.broadcast(gameState);
        }
    }

    /**
     * Checks that all mice are in their bounds and if mice have won.
     */
    private void checkState() {
        checkBounds();
        checkMiceWon();
        gameState.clearExpiredSnapshots(configuration.catSnapshotDuration());
    }

    /**
     * Checks if the mice have won the game.
     */
    private void checkMiceWon() {
        var countingMap = gameState.getMice().values().stream()
                .collect(Collectors.groupingBy(Mouse::getCurrentLevel, Collectors.counting()));
        if (countingMap.get(0) == null && countingMap.values().size() == 1) {
            gameState.setGameStatus(GameStatus.MICE_WON);
        }
    }

    /**
     * Ensures that all mice are within the game field boundaries.
     */
    private void checkBounds() {
        Collection<Mouse> mice = gameState.getMice().values();
        mice.stream().filter(m -> m.getCurrentLevel() == 0).forEach(m -> {
            var pos = m.getPos();
            var x = pos[0];
            var y = pos[1];
            var bounds = gameState.getGameField().getBounds();
            double characterSizeHalf = gameState.getGameField().getMouseSize() / 2;
            var boundsX = bounds[0] + 1 - characterSizeHalf;
            var boundsY = bounds[1] + 1 - characterSizeHalf;
            if (x > boundsX || y > boundsY || x < characterSizeHalf || y < characterSizeHalf) {
                m.setPos(new double[]{
                        Math.max(characterSizeHalf, Math.min(x, boundsX)),
                        Math.max(characterSizeHalf, Math.min(y, boundsY))
                });
            }
        });
    }

    /**
     * Checks if the game time has expired and updates the game status accordingly.
     */
    private void timeCheck() {
        if (gameState.getGameStart().plus(gameState.getGameDuration()).isBefore(LocalDateTime.now())) {
            gameState.setGameStatus(GameStatus.TIME_OUT);
        }
    }

    /**
     * Processes all queued action requests.
     */
    private void processActions() {
        while (!actionQueue.isEmpty()) {
            ActionRequestDto actionRequestDto = actionQueue.poll();
            processActionHelper(actionRequestDto);
        }
    }

    /**
     * Checks if all clients are ready to start the game.
     *
     * @return true if all clients are ready, false otherwise.
     */
    private boolean allClientsReady() {
        boolean allReady = !updateBroadcaster.getClients().isEmpty() && updateBroadcaster.getClients().stream().allMatch(ClientManager::isReady);
        if (allReady) {
            System.out.println("All clients ready");
        }
        return allReady;
    }

    /**
     * Helper method to process a single action request.
     *
     * @param actionRequestDto The action request to process.
     */
    private void processActionHelper(ActionRequestDto actionRequestDto) {
        var mouse = gameState.getMouse(actionRequestDto.getClientId());
        double[] mousePos = mouse.getPos();
        switch (actionRequestDto.getType()) {
            case DIRECTION -> {
                var direction = actionRequestDto.getDirection();
                var speedPerTick = (configuration.mouseSpeed() * gameState.getGameField().getBounds()[0] / 100d) / configuration.tickRate();
                double xChange = direction.getDirX() * speedPerTick;
                double yChange = direction.getDirY() * speedPerTick;
                if (mouse.getCurrentLevel() == 0) {
                    mouse.move(xChange, yChange);
                } else {
                    var currentTunnel = gameState.getGameField().getTunnels().get(mouse.getCurrentLevel());
                    var nextX = mousePos[0] + xChange;
                    var nextY = mousePos[1] + yChange;
                    if (currentTunnel.isPosWithinTunnel(nextX, nextY, gameState.getGameField().getMouseSize())) {
                        mouse.move(xChange, yChange);
                    }
                }
            }
            case TUNNEL_VOTE -> mouse.setTunnelVote(actionRequestDto.getTunnelVote());
            case LEVEL_CHANGE -> {
                var tunnelFound = gameState.getGameField().getTunnels().entrySet().stream()
                        .filter(e -> e.getValue().isPosWithinDoor(mousePos[0], mousePos[1], gameState.getGameField().getMouseSize())).findFirst();
                tunnelFound.ifPresent(tunnelEntry -> {
                    var currentLevel = mouse.getCurrentLevel();
                    if (currentLevel == 0) {
                        mouse.setCurrentLevel(tunnelEntry.getKey());
                        gameState.addSnapshot(tunnelEntry.getKey(), gameState.getCats());
                        System.out.println("Setting snapshot");
                        System.out.println(gameState.getCatSnapshots().get(tunnelEntry.getKey()));
                    } else {
                        mouse.setCurrentLevel(0);
                    }
                });
            }
        }
    }
}
