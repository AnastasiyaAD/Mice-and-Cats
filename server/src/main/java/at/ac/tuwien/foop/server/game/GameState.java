package at.ac.tuwien.foop.server.game;

import at.ac.tuwien.foop.server.network.ClientManager;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private final List<ClientManager> clientManagers = new ArrayList<>();


    public GameState() {
    }
}
