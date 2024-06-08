package at.ac.tuwien.foop.server.network.dto;

import lombok.Getter;

@Getter
public class HandshakeRequestDto {

    private final Handshake handshake;
    private final String username;

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

    public enum Handshake {
        REGISTER,
        FAIL_STATE,
        READY;
    }
}
