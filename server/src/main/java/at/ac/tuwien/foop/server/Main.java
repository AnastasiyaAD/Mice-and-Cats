package at.ac.tuwien.foop.server;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.GameManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        var config = new Configuration(60);
        var gameManager = new GameManager();
        new Server(8008, gameManager, config);
    }
}