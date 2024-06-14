package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.network.dto.ActionRequestDto;
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

    public GameManager(Configuration configuration) {
        this.configuration = configuration;
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
                gameState.setGameStart(LocalDateTime.now());
                gameState.setGameStatus(GameStatus.RUNNING);
            }
            timeCheck();
            processActions();
            checkState();
            updateBroadcaster.broadcast(gameState);
        }
    }

    private void checkState() {
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
        var direction = actionRequestDto.getDirection();
        var speedPerTick = configuration.mouseSpeed() / configuration.tickRate();
        mouse.move(direction.getDirX() * speedPerTick, direction.getDirY() * speedPerTick);
    }
}
