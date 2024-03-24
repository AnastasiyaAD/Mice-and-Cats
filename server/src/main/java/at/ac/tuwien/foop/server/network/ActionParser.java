package at.ac.tuwien.foop.server.network;

import at.ac.tuwien.foop.server.network.dto.ActionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActionParser {

    public static ActionRequest parse(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, ActionRequest.class);
    }

}
