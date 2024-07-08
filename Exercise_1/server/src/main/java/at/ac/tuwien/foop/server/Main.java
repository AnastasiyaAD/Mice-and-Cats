package at.ac.tuwien.foop.server;

import at.ac.tuwien.foop.server.game.Configuration;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) throws IOException {
        var config = new Configuration(60, 50, 40, Duration.of(3, ChronoUnit.SECONDS),
                3, 0);
        var server = new Server(8008, config);
        server.start();
    }
}