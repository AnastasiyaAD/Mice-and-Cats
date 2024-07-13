package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.network.dto.DtoParser;
import at.ac.tuwien.foop.server.game.GameManager;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

import static at.ac.tuwien.foop.network.dto.HandshakeRequestDto.Handshake.READY;
import static at.ac.tuwien.foop.network.dto.HandshakeRequestDto.Handshake.REGISTER;

/**
 * Manages the communication between the server and a client for a game.
 * Handles the initialization, handshake, and continuous reading of client actions.
 */
@Getter
public class ClientManager implements Runnable {

    private final Socket socket;
    private final GameManager gameManager;
    private final UUID clientId = UUID.randomUUID();
    private String username;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean ready = false;

    /**
     * Constructor for ClientManager.
     *
     * @param socket      The socket representing the connection to the client.
     * @param gameManager The GameManager that manages the game state and actions.
     */
    public ClientManager(Socket socket, GameManager gameManager) {
        this.socket = socket;
        this.gameManager = gameManager;
    }

    /**
     * Sends game state to the client.
     *
     * @param gameState The current state of the game to be sent to the client.
     */
    public void sendData(String gameState) {
        writer.println(gameState);
    }

    /**
     * The main loop of the client manager, initializing the connection and processing client actions.
     */
    @Override
    public void run() {
        this.init();
        this.handshake();
        try (var input = socket.getInputStream()) {
            var scanner = new Scanner(input, StandardCharsets.UTF_8);

            while (scanner.hasNextLine()) {
                var actionJson = scanner.nextLine();
                var actionRequest = DtoParser.parseActionRequest(actionJson);
                actionRequest.setClientId(this.clientId);
                gameManager.queueAction(actionRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the input and output streams for the socket.
     */
    private void init() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the handshake process with the client, including registration and readiness checks.
     */
    private void handshake() {
        try {
            var registrationData = reader.readLine();
            var registerHandshake = DtoParser.parseHandshake(registrationData);
            if (registerHandshake.getHandshake() != REGISTER) {
                throw new RuntimeException("Handshake fail! Expected REGISTER but got " + registerHandshake.getHandshake());
            }
            this.username = registerHandshake.getUsername();
            this.gameManager.registerClient(this);
            writer.println(this.clientId);

            var readyData = reader.readLine();
            registerHandshake = DtoParser.parseHandshake(readyData);
            if (registerHandshake.getHandshake() != READY) {
                throw new RuntimeException("Handshake fail! Expected READY but got " + registerHandshake.getHandshake());
            }
            System.out.printf("Client %s ready!%n", this.clientId);
            this.ready = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
