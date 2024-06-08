package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.Mouse;
import at.ac.tuwien.foop.server.network.ClientManager;
import at.ac.tuwien.foop.server.network.UpdateBroadcaster;
import at.ac.tuwien.foop.server.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.server.network.dto.Direction;
import at.ac.tuwien.foop.server.network.dto.HandshakeRequestDto;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameManager {

    private final GameState gameState = new GameState();
    private final UpdateBroadcaster updateBroadcaster = new UpdateBroadcaster();
    private final ConcurrentLinkedQueue<ActionRequestDto> actionQueue = new ConcurrentLinkedQueue<>();

    public synchronized void queueAction(ActionRequestDto actionRequestDto) {
        this.actionQueue.add(actionRequestDto);
    }

    public void registerClient(ClientManager clientManager) {
        var newMouse = new Mouse(clientManager.getClientId(), clientManager.getUsername());
        this.gameState.getMice().add(newMouse);
        this.updateBroadcaster.addClient(clientManager);
        System.out.printf("Registered user %s, with clientId %s%n", clientManager.getUsername(), clientManager.getClientId());
    }

    public void updateGame() {
        if (this.gameState.getGameStart() != null || allClientsReady()) {
            this.gameState.setGameStart(LocalDateTime.now());
            this.processActions();
            this.updateBroadcaster.broadcast(this.gameState);
        }
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
        System.out.println("Processing action request: " + actionRequestDto);
        // TODO: calculate speed on server side (direction only tells direction, not speed)
        var mouse = this.gameState.getMice().stream().filter(m -> m.getClientId().equals(actionRequestDto.getClientId())).findFirst().orElseThrow(() -> new RuntimeException("Client not found"));
        final Direction direction = actionRequestDto.getDirection();
        mouse.changePosX(direction.getDirX());
        mouse.changePosY(direction.getDirY());
    }
}
