package at.ac.tuwien.foop.server;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.GameManager;
import at.ac.tuwien.foop.server.network.dto.ActionRequestDto;
import at.ac.tuwien.foop.server.network.dto.Direction;
import at.ac.tuwien.foop.server.network.dto.HandshakeRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.fail;


class ServerTest {


    @Test
    void name() throws IOException {
        var config = new Configuration(60);
        var gameManager = new GameManager();
        int port = 8008;
        var server = new Server(port, gameManager, config);
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