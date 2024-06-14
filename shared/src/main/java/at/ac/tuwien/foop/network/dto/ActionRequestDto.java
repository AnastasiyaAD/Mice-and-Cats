package at.ac.tuwien.foop.network.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ActionRequestDto {

    private final Direction direction;
    private final Integer tunnelVote;
    private UUID clientId;

    public ActionRequestDto(Direction direction) {
        this.direction = direction;
        this.tunnelVote = null;
    }

    public ActionRequestDto(Integer tunnelVote) {
        this.tunnelVote = tunnelVote;
        this.direction = null;
    }
}
