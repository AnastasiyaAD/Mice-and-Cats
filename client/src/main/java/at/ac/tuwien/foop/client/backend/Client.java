package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.client.GameBoardPanel;
import at.ac.tuwien.foop.client.Mouse;
import at.ac.tuwien.foop.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.network.dto.Direction;
import at.ac.tuwien.foop.network.dto.GameStateDto;
import at.ac.tuwien.foop.network.dto.HandshakeRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements IClient, AutoCloseable {

  private Socket socket;
  private PrintWriter out;
  private Thread listenerThread;
  private GameStateListener gameStateListener;
  private static Client client;
  private DataInputStream in;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void connect(String host, int port) throws IOException {
    socket = new Socket(host, port);
    socket.getOutputStream();
    socket.getInputStream();
    out = new PrintWriter(socket.getOutputStream(), true);
    in = new DataInputStream(socket.getInputStream());
  }

  public static Client getGameClient() {
    if (client == null) client = new Client();
    return client;
  }

  public void register(
    Mouse clientMouse,
    String username,
    GameBoardPanel boardPanel
  ) {
    new GameStateListener(clientMouse, username, boardPanel, in).start();
    var handshakeRequest = HandshakeRequestDto.registerHandshakeRequest(
      username
    );
    try {
      send(objectMapper.writeValueAsString(handshakeRequest));
    } catch (JsonProcessingException e) {
      // TODO: handle exception
    }
  }

  public Socket getSocket() {
    return socket;
  }

  public void initiateReady(String username) throws IOException {
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
  }

  public void sendDirection(Direction direction) {
    var actionRequest = new ActionRequestDto(direction);
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
      GameStateDto gameStateDto = objectMapper.readValue(
        json,
        GameStateDto.class
      );
    } catch (JsonProcessingException e) {
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
    out.close();
    socket.close();
  }
}
