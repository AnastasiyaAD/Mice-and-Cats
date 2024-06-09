package at.ac.tuwien.foop.server;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.GameManager;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.Mouse;
import at.ac.tuwien.foop.server.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.server.network.dto.Direction;
import at.ac.tuwien.foop.server.network.dto.HandshakeRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;


class ServerTest {

    @Test
    void test() throws JsonProcessingException {
        var gameState = new GameState();
        gameState.setGameStart(LocalDateTime.now());
        gameState.addMouse(UUID.randomUUID(), new Mouse("Test mouse 1"));
        gameState.addMouse(UUID.randomUUID(), new Mouse("Test mouse 2"));
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        System.out.println(objectMapper.writeValueAsString(gameState));
    }

    @Test
    void name() throws IOException {
        var config = new Configuration(60, 300, 320);
        int port = 8008;
        var server = new Server(port, config);
        server.start();

        var mapper = new ObjectMapper();
        try (Socket socket = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            var handshakeRegister = HandshakeRequestDto.registerHandshakeRequest("Test user");
            out.println(mapper.writeValueAsString(handshakeRegister));
            var handshakeReady = HandshakeRequestDto.readyHandshakeRequest();
            out.println(mapper.writeValueAsString(handshakeReady));

            var actionRequest = new ActionRequestDto(Direction.SOUTH);
            out.println(mapper.writeValueAsString(actionRequest));

            var serverResponse = in.readLine();
            System.out.println(serverResponse);
            while (true) {

            }
        } catch (IOException e) {
            e.printStackTrace();
            server.stop();
            fail();
        }
        server.stop();
    }
}