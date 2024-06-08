package at.ac.tuwien.foop.server.network.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class HandshakeRequestDto {

    private final Handshake handshake;
    private final String username;
    private UUID clientId;

    private HandshakeRequestDto(Handshake handshake) {
        this.handshake = handshake;
        this.username = null;
    }

    private HandshakeRequestDto(Handshake handshake, String username) {
        this.handshake = handshake;
        this.username = username;
    }

    public static HandshakeRequestDto registerHandshakeRequest(String username) {
        return new HandshakeRequestDto(Handshake.REGISTER, username);
    }

    public static HandshakeRequestDto readyHandshakeRequest() {
        return new HandshakeRequestDto(Handshake.READY);
    }

    @JsonCreator
    public static HandshakeRequestDto create(@JsonProperty("handshake") Handshake handshake,
                                             @JsonProperty("username") String username) {
        return new HandshakeRequestDto(handshake, username);
    }

    public enum Handshake {
        REGISTER,
        FAIL_STATE,
        READY;
    }
}
