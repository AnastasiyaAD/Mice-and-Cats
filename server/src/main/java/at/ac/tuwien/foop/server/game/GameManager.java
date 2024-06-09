package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.Mouse;
import at.ac.tuwien.foop.server.network.ClientManager;
import at.ac.tuwien.foop.server.network.UpdateBroadcaster;
import at.ac.tuwien.foop.server.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.server.network.dto.Direction;
import at.ac.tuwien.foop.server.network.dto.HandshakeRequestDto;

import java.time.LocalDateTime;
import java.util.Vector;
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
        this.actionQueue.add(actionRequestDto);
    }

    public void registerClient(ClientManager clientManager) {
        var newMouse = new Mouse(clientManager.getUsername());
        this.gameState.addMouse(clientManager.getClientId(), newMouse);
        this.updateBroadcaster.addClient(clientManager);
        System.out.printf("Registered user %s, with clientId %s%n", clientManager.getUsername(), clientManager.getClientId());
    }

    public void updateGame() {
        if (this.gameState.getGameStart() != null || allClientsReady()) {
            this.gameState.setGameStart(LocalDateTime.now());
            this.processActions();
            this.checkState();
            this.updateBroadcaster.broadcast(this.gameState);
        }
    }

    private void checkState() {
        this.gameState.getMice().values().forEach(m -> {
            var pos = m.getPos();
            var x = pos[0];
            var y = pos[1];
            var bounds = this.gameState.getGameField().getBounds();
            var boundsX = bounds[0];
            var boundsY = bounds[1];
            if (x > boundsX || y > boundsY) {
                m.setPos(new double[]{Math.max(x, boundsX), Math.max(y, boundsY)});
            }
        });
    }

    private void processActions() {
        while (!this.actionQueue.isEmpty()) {
            ActionRequestDto actionRequestDto = this.actionQueue.poll();
            this.processActionHelper(actionRequestDto);
        }
    }

    private boolean allClientsReady() {
        boolean allReady = !this.updateBroadcaster.getClients().isEmpty() && this.updateBroadcaster.getClients().stream().allMatch(ClientManager::isReady);
        if (allReady) {
            System.out.println("All clients ready");
        }
        return allReady;
    }

    private void processActionHelper(ActionRequestDto actionRequestDto) {
        var mouse = this.gameState.getMouse(actionRequestDto.getClientId());
        var direction = actionRequestDto.getDirection();
        var speedPerTick = configuration.mouseSpeed() / configuration.tickRate();
        mouse.move(direction.getDirX() * speedPerTick, direction.getDirY() * speedPerTick);
    }
}
