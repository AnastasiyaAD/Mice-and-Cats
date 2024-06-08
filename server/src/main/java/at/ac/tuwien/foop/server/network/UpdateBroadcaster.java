package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.server.game.GameState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UpdateBroadcaster {

    private final Set<ClientManager> clients = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void addClient(ClientManager clientHandler) {
        clients.add(clientHandler);
    }

    public void removeClient(ClientManager clientHandler) {
        clients.remove(clientHandler);
    }

    public void broadcast(GameState state) {
        String stateJson = null;
        try {
            stateJson = convertStateToJson(state);
            for (ClientManager client : clients) {
                client.send(stateJson);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertStateToJson(GameState state) throws JsonProcessingException {
        // Assuming you're using Gson for JSON serialization
        return new ObjectMapper().writeValueAsString(state);
    }
}
