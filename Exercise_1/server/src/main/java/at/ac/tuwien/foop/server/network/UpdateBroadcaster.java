package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.mapper.GameStateMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages broadcasting game state updates to all connected clients.
 */
@Getter
public class UpdateBroadcaster {

    private final Set<ClientManager> clients = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Adds a client to the set of clients to receive updates.
     *
     * @param clientHandler The client manager representing the client to be added.
     */
    public void addClient(ClientManager clientHandler) {
        clients.add(clientHandler);
    }

    /**
     * Removes a client from the set of clients to receive updates.
     *
     * @param clientHandler The client manager representing the client to be removed.
     */
    public void removeClient(ClientManager clientHandler) {
        clients.remove(clientHandler);
    }

    /**
     * Broadcasts the current game state to all connected clients.
     *
     * @param state The current game state to be broadcasted.
     */
    public void broadcast(GameState state) {
        String stateJson;
        var dto = GameStateMapper.toDto(state);
        try {
            stateJson = convertStateToJson(dto);
            for (ClientManager client : clients) {
                client.sendData(stateJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the game state DTO to a JSON string.
     *
     * @param state The game state DTO to be converted.
     * @return The JSON string representation of the game state.
     * @throws JsonProcessingException If an error occurs during JSON processing.
     */
    private String convertStateToJson(GameStateDto state) throws JsonProcessingException {
        return objectMapper.writeValueAsString(state);
    }
}
