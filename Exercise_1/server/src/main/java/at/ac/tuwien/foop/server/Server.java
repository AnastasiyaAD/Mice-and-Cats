package at.ac.tuwien.foop.server;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.GameManager;
import at.ac.tuwien.foop.server.network.ClientManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Manages the server-side operations of the game, including accepting client connections
 * and coordinating game ticks.
 */
public class Server {

    private final ServerSocket serverSocket;
    private final ExecutorService clientThreadPool;
    private final ScheduledExecutorService gameTickExecutor;
    private final GameManager gameManager;
    private final Configuration configuration;
    private boolean stop = false;

    /**
     * Constructs a Server object.
     *
     * @param port          The port on which the server will listen for client connections.
     * @param configuration The configuration settings for the server and game.
     * @throws IOException If an I/O error occurs when opening the socket.
     */
    public Server(int port, Configuration configuration) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.configuration = configuration;
        this.clientThreadPool = Executors.newCachedThreadPool();
        this.gameTickExecutor = Executors.newScheduledThreadPool(1);
        this.gameManager = new GameManager(configuration);
    }

    /**
     * Starts the server to listen for client connections and begin game ticking.
     */
    public void start() {
        var clientListenerThread = new Thread(() -> {
            try {
                listenForClients();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        clientListenerThread.start();
        this.startGameTick();
    }

    /**
     * Stops the server and shuts down all executor services.
     *
     * @throws IOException If an I/O error occurs when closing the server socket.
     */
    public void stop() throws IOException {
        System.out.println("Stopping server");
        this.stop = true;
        this.clientThreadPool.shutdown();
        this.gameTickExecutor.shutdown();
        this.serverSocket.close();
    }

    /**
     * Starts the scheduled game ticks at the interval specified in the configuration.
     */
    private void startGameTick() {
        var tickInterval = 1000 / configuration.tickRate();
        gameTickExecutor.scheduleAtFixedRate(gameManager::updateGame, 0, tickInterval, TimeUnit.MILLISECONDS);
    }

    /**
     * Listens for client connections and starts a new thread for each connected client.
     *
     * @throws IOException If an I/O error occurs when waiting for a connection.
     */
    private void listenForClients() throws IOException {
        System.out.println("Server started. Listening for connections...");
        while (!this.stop) {
            Socket clientSocket = serverSocket.accept();
            ClientManager clientHandler = new ClientManager(clientSocket, gameManager);
            clientThreadPool.execute(clientHandler);
        }
        System.out.println("Stopping listening for connections...");
    }
}
