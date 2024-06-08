package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.server.game.GameManager;
import at.ac.tuwien.foop.server.game.GameState;
import at.ac.tuwien.foop.server.network.dto.ActionRequest;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

@AllArgsConstructor
public class ClientManager implements Runnable {

    private Socket socket;
    private GameManager gameManager;
    private UUID clientId = UUID.randomUUID();

    public void send(GameState gameState) {
    }

    @Override
    public void run() {
        this.handshake();
        this.gameManager.registerClient(this);
        try (var input = socket.getInputStream()) {
            var scanner = new Scanner(input, StandardCharsets.UTF_8);

            while (scanner.hasNextLine()) {
                var actionJson = scanner.nextLine();
                var actionRequest = ActionParser.parse(actionJson);
                gameManager.queueAction(actionRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handshake() {
        try (var writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(clientId);
        } catch (IOException e) {
            // TODO handle error
        }
    }
}

