package at.ac.tuwien.foop.client.backend;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Listens for game state updates from the server and notifies the client.
 */
public class GameStateListener implements Runnable {

    private BufferedReader in;
    private IClient client;
    private boolean running = true;

    /**
     * Constructs a GameStateListener.
     *
     * @param in     The BufferedReader to read messages from the server.
     * @param client The client to notify of received messages.
     */
    public GameStateListener(BufferedReader in, IClient client) {
        this.in = in;
        this.client = client;
    }

    /**
     * Runs the listener loop, reading messages from the server and passing them to the client.
     */
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double ticks = 60D;
        double ns = 1000000000 / ticks;
        double delta = 0;
        try {
            String message;
            while (running && (message = in.readLine()) != null) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if (delta >= 1) {
                    client.receive(message);
                    delta--;
                }
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the listener loop.
     */
    public void stop() {
        running = false;
    }
}
