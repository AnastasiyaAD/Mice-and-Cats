package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.network.ClientManager;
import at.ac.tuwien.foop.server.network.UpdateBroadcaster;
import at.ac.tuwien.foop.server.network.dto.ActionRequestDto;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameManager {

    private final GameState gameState = new GameState();
    private final UpdateBroadcaster updateBroadcaster = new UpdateBroadcaster();
    private final ConcurrentLinkedQueue<ActionRequestDto> actionQueue = new ConcurrentLinkedQueue<>();


    public synchronized void queueAction(ActionRequestDto actionRequestDto) {
        this.actionQueue.add(actionRequestDto);
    }

    public void registerClient(ClientManager clientManager) {
        this.updateBroadcaster.addClient(clientManager);
        System.out.printf("Registered user %s, with clientId %s%n", clientManager.getUsername(), clientManager.getClientId());
    }

    public void updateGame() {
        this.processActions();
        this.updateBroadcaster.broadcast(this.gameState);
    }

    private void processActions() {
        while (!this.actionQueue.isEmpty()) {
            ActionRequestDto actionRequestDto = this.actionQueue.poll();
            this.processActionHelper(actionRequestDto);
        }
    }

    private void processActionHelper(ActionRequestDto actionRequestDto) {

    }

}
