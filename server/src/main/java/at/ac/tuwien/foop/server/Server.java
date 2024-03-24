package at.ac.tuwien.foop.server;

import at.ac.tuwien.foop.server.network.ClientManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService threadPool; // For handling multiple client connections
    private GameManager gameManager;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.threadPool = Executors.newCachedThreadPool();
        this.gameManager = new GameManager();
    }

    public void start() throws IOException {
        System.out.println("Server started. Listening for connections...");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientManager clientHandler = new ClientManager(clientSocket, gameManager);
            threadPool.execute(clientHandler);
        }
    }
}
