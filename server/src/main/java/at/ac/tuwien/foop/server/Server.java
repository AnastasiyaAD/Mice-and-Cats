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

public class Server {

    private final ServerSocket serverSocket;
    private final ExecutorService clientThreadPool;
    private final ScheduledExecutorService gameTickExecutor;
    private final GameManager gameManager;
    private final Configuration configuration;

    public Server(int port, GameManager gameManager, Configuration configuration) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.configuration = configuration;
        this.clientThreadPool = Executors.newCachedThreadPool();
        this.gameTickExecutor = Executors.newScheduledThreadPool(1);
        this.gameManager = gameManager;
    }

    public void start() {
        var clientListenerThread = new Thread(() -> {
            try {
                listenForClients();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        clientListenerThread.start();

    }

    private void startGameTick() {
        var tickInterval = configuration.tickRate() / 1000;
        gameTickExecutor.scheduleAtFixedRate(gameManager::updateGame, 0, tickInterval, TimeUnit.MILLISECONDS);
    }

    private void listenForClients() throws IOException {
        System.out.println("Server started. Listening for connections...");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientManager clientHandler = new ClientManager(clientSocket, gameManager);
            clientThreadPool.execute(clientHandler);
        }
    }
}
