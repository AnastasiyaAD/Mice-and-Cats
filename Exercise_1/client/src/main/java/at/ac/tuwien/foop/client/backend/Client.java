package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.client.GameBoardPanel;
import at.ac.tuwien.foop.client.GameChatPanel;
import at.ac.tuwien.foop.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.network.dto.Direction;
import at.ac.tuwien.foop.network.dto.GameStateDto;
import at.ac.tuwien.foop.network.dto.HandshakeRequestDto;
import at.ac.tuwien.foop.network.dto.LevelChangeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a client in the "Mice and Cats in a Network Game". Manages the connection,
 * sending and receiving data from the server, and updating the game state.
 */
public class Client implements IClient, AutoCloseable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread listenerThread;
    private GameStateListener gameStateListener;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final GameBoardPanel gameBoardPanel;
    private final GameChatPanel gameChatPanel;
    private LevelChangeRequest levelChangeRequest;
    private Set<Direction> directions;

    /**
     * Creates a new instance of Client.
     *
     * @param gameBoardPanel The game board panel to be updated with the game state.
     * @param gameChatPanel  The game chat panel to be updated with the game state.
     */
    public Client(GameBoardPanel gameBoardPanel, GameChatPanel gameChatPanel) {
        this.gameBoardPanel = gameBoardPanel;
        this.gameChatPanel = gameChatPanel;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.objectMapper = mapper;
        this.directions = new HashSet<>();
    }

    /**
     * Connects to the server with the specified host and port and starts listening to game updates from the server,
     * if successful.
     *
     * @param host The host of the server.
     * @param port The port of the server.
     * @throws IOException          If an I/O error occurs when creating the socket.
     * @throws UnknownHostException If the IP address of the host could not be determined.
     */
    // Pre: Valid host and port to game server
    // Post: Socket to game server is established
    // Post: Client starts listening to any updates from the server
    @Override
    public void connect(String host, int port) throws IOException, UnknownHostException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        gameStateListener = new GameStateListener(in, this);
        listenerThread = new Thread(gameStateListener);
    }

    /**
     * Registers the client with the specified username.
     *
     * @param username The username to register with.
     * @return The UUID of the registered client.
     */
    // Post server registers that said client with username is participating in game
    public UUID register(String username) {
        var handshakeRequest = HandshakeRequestDto.registerHandshakeRequest(username);
        try {
            send(objectMapper.writeValueAsString(handshakeRequest));
            var clientId = in.readLine();
            listenerThread.start();
            return UUID.fromString(clientId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initiates the ready handshake request.
     *
     * @param username The username to be set as ready.
     */
    // Pre: InitializeReady has to be called before
    // Post: Server knows client is ready
    public void initiateReady(String username) {
        var handshakeRequest = HandshakeRequestDto.readyHandshakeRequest();
        try {
            send(objectMapper.writeValueAsString(handshakeRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects the client from the server.
     *
     * @throws Exception If an error occurs while closing the connection.
     */
    @Override
    public void disconnect() throws Exception {
        close();
    }

    /**
     * Sends a message to the server.
     *
     * @param message The message to be sent.
     */
    private void send(String message) {
        out.println(message);
        if (out.checkError()) {
            throw new RuntimeException();
        }
    }

    /**
     * Sends a direction action request to the server.
     *
     * @param direction The direction to be sent.
     */
    public void sendDirection(Direction direction) {
        var actionRequest = new ActionRequestDto(direction);
        try {
            send(objectMapper.writeValueAsString(actionRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a direction to the set of directions.
     *
     * @param direction The direction to be added.
     */
    public void addDirection(Direction direction) {
        this.directions.add(direction);
    }

    /**
     * Removes a direction from the set of directions.
     *
     * @param direction The direction to be removed.
     */
    public void removeDirection(Direction direction) {
        this.directions.remove(direction);
    }

    /**
     * Sends a level change action request to the server.
     */
    public void sendLevelChange() {
        var actionRequest = new ActionRequestDto(levelChangeRequest);
        try {
            send(objectMapper.writeValueAsString(actionRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a tunnel vote action request to the server.
     *
     * @param tunnelVote The tunnel vote to be sent.
     */
    public void sendMessage(Integer tunnelVote) {
        var actionRequest = new ActionRequestDto(tunnelVote);
        try {
            send(objectMapper.writeValueAsString(actionRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receives a JSON message from the server, updates the game state, and sends directions.
     *
     * @param json The JSON message received from the server.
     */
    @Override
    public void receive(String json) {
        var set = new HashSet<>(this.directions);
        for (var dir : set) {
            this.sendDirection(dir);
        }
        try {
            GameStateDto gameStateDto = objectMapper.readValue(json, GameStateDto.class);
            gameBoardPanel.updateBoard(gameStateDto);
            gameChatPanel.updateBoard(gameStateDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Closes the client connection and stops the listener thread.
     *
     * @throws Exception If an error occurs while closing the connection.
     */
    @Override
    public void close() throws Exception {
        if (gameStateListener != null) {
            gameStateListener.stop();
        }
        if (listenerThread != null) {
            listenerThread.join();
        }
        in.close();
        out.close();
        socket.close();
    }
}
