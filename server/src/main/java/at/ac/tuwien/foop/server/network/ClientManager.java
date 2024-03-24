package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.server.GameManager;
import at.ac.tuwien.foop.server.network.dto.ActionRequest;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@AllArgsConstructor
public class ClientManager implements Runnable {

    private Socket socket;
    private GameManager gameManager;

    public void send(String state) {
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream()) {
            Scanner scanner = new Scanner(input, StandardCharsets.UTF_8);

            while (scanner.hasNextLine()) {
                String actionJson = scanner.nextLine();
                ActionRequest actionRequest = ActionParser.parse(actionJson);
                gameManager.processAction(actionRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO Equals & hashcode
}

