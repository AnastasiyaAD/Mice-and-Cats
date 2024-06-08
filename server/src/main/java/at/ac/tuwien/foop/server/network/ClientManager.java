package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.server.game.GameManager;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

import static at.ac.tuwien.foop.server.network.dto.HandshakeRequestDto.Handshake.READY;
import static at.ac.tuwien.foop.server.network.dto.HandshakeRequestDto.Handshake.REGISTER;

@AllArgsConstructor

public class ClientManager implements Runnable {

    private Socket socket;
    private GameManager gameManager;
    private UUID clientId = UUID.randomUUID();
    private String username;
    private BufferedReader reader;
    private PrintWriter writer;

    public void sendGameState(String gameState) {
        try (var writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(gameState);
        } catch (IOException e) {
            // TODO handle error
        }
    }

    @Override
    public void run() {
        this.init();
        this.handshake();
        try (var input = socket.getInputStream()) {
            var scanner = new Scanner(input, StandardCharsets.UTF_8);

            while (scanner.hasNextLine()) {
                var actionJson = scanner.nextLine();
                var actionRequest = DtoParser.parseActionRequest(actionJson);
                gameManager.queueAction(actionRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            // TODO: Ex handling
            throw new RuntimeException(e);
        }
    }

    private void handshake() {
        try {
            var registrationData = reader.readLine();
            var registerHandshake = DtoParser.parseHandshake(registrationData);
            if (registerHandshake.getHandshake() != REGISTER) {
                throw new RuntimeException("Handshake fail! Expected REGISTER but got " + registerHandshake.getHandshake());
            }
            this.username = registerHandshake.getUsername();

            var readyData = reader.readLine();
            registerHandshake = DtoParser.parseHandshake(readyData);
            if (registerHandshake.getHandshake() != READY) {
                throw new RuntimeException("Handshake fail! Expected READY but got " + registerHandshake.getHandshake());
            }
            this.gameManager.registerClient(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

