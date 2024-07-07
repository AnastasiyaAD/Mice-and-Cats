package at.ac.tuwien.foop.network.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DtoParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ActionRequestDto parseActionRequest(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, ActionRequestDto.class);
    }

    public static HandshakeRequestDto parseHandshake(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, HandshakeRequestDto.class);
    }

}
