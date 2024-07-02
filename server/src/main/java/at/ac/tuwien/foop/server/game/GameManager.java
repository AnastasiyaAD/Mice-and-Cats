package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.server.game.cats.CatManager;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.GameStatus;
import at.ac.tuwien.foop.server.game.state.Mouse;
import at.ac.tuwien.foop.server.network.ClientManager;
import at.ac.tuwien.foop.server.network.UpdateBroadcaster;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameManager {

    private final GameState gameState = new GameState();
    private final UpdateBroadcaster updateBroadcaster = new UpdateBroadcaster();
    private final ConcurrentLinkedQueue<ActionRequestDto> actionQueue = new ConcurrentLinkedQueue<>();
    private final Configuration configuration;
    private final CatManager catManager;

    public GameManager(Configuration configuration) {
        this.configuration = configuration;
        this.catManager = new CatManager();
    }

    public synchronized void queueAction(ActionRequestDto actionRequestDto) {
        actionQueue.add(actionRequestDto);
    }

    public void registerClient(ClientManager clientManager) {
        var newMouse = new Mouse(clientManager.getUsername());
        gameState.addMouse(clientManager.getClientId(), newMouse);
        updateBroadcaster.addClient(clientManager);
        System.out.printf("Registered user %s, with clientId %s%n", clientManager.getUsername(), clientManager.getClientId());
    }

    public void updateGame() {
        if (gameState.getGameStart() != null || allClientsReady()) {
            if (gameState.getGameStart() == null) {
                catManager.spawnCats(gameState);
                gameState.setGameStart(LocalDateTime.now());
                gameState.setGameStatus(GameStatus.RUNNING);
            }
            timeCheck();
            // FIXME: Not completely sure where we should simulate the cats
            processActions();
            catManager.updateCats(gameState);
            checkState();
            updateBroadcaster.broadcast(gameState);
        }
    }

    private void checkState() {
        checkBounds();
        checkTunnels();
    }

    private void checkTunnels() {
        gameState.getMice().values().forEach(m -> {

        });
    }

    private void checkBounds() {
        gameState.getMice().values().forEach(m -> {
            var pos = m.getPos();
            var x = pos[0];
            var y = pos[1];
            var bounds = gameState.getGameField().getBounds();
            var boundsX = bounds[0];
            var boundsY = bounds[1];
            if (x > boundsX || y > boundsY) {
                m.setPos(new double[]{Math.max(0, Math.min(x, boundsX)), Math.max(0, Math.min(y, boundsY))});
            }
        });
    }

    private void timeCheck() {
        if (gameState.getGameStart().plus(gameState.getGameDuration()).isAfter(LocalDateTime.now())) {
            // TODO: check who won?
            gameState.setGameStatus(GameStatus.TIME_OUT);
        }
    }

    private void processActions() {
        while (!actionQueue.isEmpty()) {
            ActionRequestDto actionRequestDto = actionQueue.poll();
            processActionHelper(actionRequestDto);
        }
    }

    private boolean allClientsReady() {
        boolean allReady = !updateBroadcaster.getClients().isEmpty() && updateBroadcaster.getClients().stream().allMatch(ClientManager::isReady);
        if (allReady) {
            System.out.println("All clients ready");
        }
        return allReady;
    }

    private void processActionHelper(ActionRequestDto actionRequestDto) {
        var mouse = gameState.getMouse(actionRequestDto.getClientId());
        switch (actionRequestDto.getType()) {
            case DIRECTION -> {
                var direction = actionRequestDto.getDirection();
                var speedPerTick = configuration.mouseSpeed() / configuration.tickRate();
                mouse.move(direction.getDirX() * speedPerTick, direction.getDirY() * speedPerTick);
            }
            case TUNNEL_VOTE -> mouse.setTunnelVote(actionRequestDto.getTunnelVote());
            case LEVEL_CHANGE -> {
                var toLevel = actionRequestDto.getLevelChangeRequest().toLevel();
                var currentLevel = mouse.getCurrentLevel();
                if (toLevel != currentLevel) {
                    if (currentLevel != 0 && toLevel == 0) {
                        var currentTunnel = gameState.getGameField().getTunnels().get(currentLevel);
                        if (currentTunnel.isPosWithinDoor(mouse.getPos()[0], mouse.getPos()[1])) {
                            mouse.setCurrentLevel(0);
                        }
                    } else if (currentLevel == 0) {
                        var targetTunnel = gameState.getGameField().getTunnels().get(toLevel);
                        if (targetTunnel.isPosWithinDoor(mouse.getPos()[0], mouse.getPos()[1])) {
                            mouse.setCurrentLevel(toLevel);
                        }
                    }
                }
            }
        }
    }
}
