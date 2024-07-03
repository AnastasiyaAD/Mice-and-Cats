package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.client.GameBoardPanel;
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

public class Client implements IClient, AutoCloseable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread listenerThread;
    private GameStateListener gameStateListener;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final GameBoardPanel gameBoardPanel;
    private LevelChangeRequest levelChangeRequest;

    public Client(GameBoardPanel gameBoardPanel) {
        this.gameBoardPanel = gameBoardPanel;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        this.objectMapper = mapper;
    }

    @Override
    public void connect(String host, int port) throws IOException, UnknownHostException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        gameStateListener = new GameStateListener(in, this);
        listenerThread = new Thread(gameStateListener);
        // TODO: Check when we want to start listening
        listenerThread.start();
            // Handshake first then we can listen to message
    }

    public void register(String username) {
        var handshakeRequest = HandshakeRequestDto.registerHandshakeRequest(username);
        try {
            send(objectMapper.writeValueAsString(handshakeRequest));
        } catch (JsonProcessingException e) {
            // TODO: handle exception
        }
    }

    public void initiateReady (String username) {
        var handshakeRequest = HandshakeRequestDto.readyHandshakeRequest();
        try {
            send(objectMapper.writeValueAsString(handshakeRequest));
        } catch (JsonProcessingException e) {
            // TODO: handle exception
        }
    }

    @Override
    public void disconnect() throws Exception {
        close();
    }

    private void send(String message) {
        out.println(message);
        if (out.checkError()) {
            throw new RuntimeException();
        }
    }

    public void sendDirection(Direction direction) {
        var actionRequest = new ActionRequestDto(direction);
        try {
            send(objectMapper.writeValueAsString(actionRequest));
        } catch (JsonProcessingException e) {
            // TODO: handle exception
        }
    }

  public void sendLevelChange() {
    var actionRequest = new ActionRequestDto(levelChangeRequest);
    try {
      send(objectMapper.writeValueAsString(actionRequest));
    } catch (JsonProcessingException e) {
      // TODO: handle exception
    }
  }

  @Override
    public void receive(String json) {
        // TODO: Hook into GUI here
        try {
            // Game state received from server
            GameStateDto gameStateDto = objectMapper.readValue(json, GameStateDto.class);
            gameBoardPanel.updateBoard(gameStateDto);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            // TODO: Handle Exception
        }
    }

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