package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.network.ClientManager;
import at.ac.tuwien.foop.server.network.UpdateBroadcaster;
import at.ac.tuwien.foop.server.network.dto.ActionRequest;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameManager {

    private final GameState gameState = new GameState();
    private final UpdateBroadcaster updateBroadcaster = new UpdateBroadcaster();
    private final ConcurrentLinkedQueue<ActionRequest> actionQueue = new ConcurrentLinkedQueue<>();


    public synchronized void queueAction(ActionRequest actionRequest) {
        this.actionQueue.add(actionRequest);
    }

    public void registerClient(ClientManager clientManager) {
        this.updateBroadcaster.addClient(clientManager);
    }

    public void updateGame() {
        this.processActions();
        this.updateBroadcaster.broadcast(this.gameState);
    }

    private void processActions() {
        while (!this.actionQueue.isEmpty()) {
            ActionRequest actionRequest = this.actionQueue.poll();
            this.processActionHelper(actionRequest);
        }
    }

    private void processActionHelper(ActionRequest actionRequest) {

    }

}
