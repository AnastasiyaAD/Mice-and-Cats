package at.ac.tuwien.foop.server;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.GameManager;
import at.ac.tuwien.foop.server.network.dto.HandshakeRequestDto;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        var config = new Configuration(60, 300, 320);
        var server = new Server(8008, config);
        server.start();
    }
}