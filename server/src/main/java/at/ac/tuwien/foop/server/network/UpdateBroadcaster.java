package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.server.game.state.GameState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UpdateBroadcaster {

    private final Set<ClientManager> clients = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

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
                client.sendGameState(stateJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String convertStateToJson(GameState state) throws JsonProcessingException {
        // Assuming you're using Gson for JSON serialization
        return objectMapper.writeValueAsString(state);
    }
}
