package at.ac.tuwien.foop.client.backend;

import java.io.BufferedReader;
import java.io.IOException;

public class GameStateListener implements Runnable {
    private BufferedReader in;
    private IClient client;
    private boolean running = true;

    public GameStateListener(BufferedReader in, IClient client) {
        this.in = in;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            String message;
            while (running && (message = in.readLine()) != null) {
                client.receive(message);
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
