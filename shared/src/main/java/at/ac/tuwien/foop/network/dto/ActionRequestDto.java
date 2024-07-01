package at.ac.tuwien.foop.network.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ActionRequestDto {

    private final Direction direction;
    private final Integer tunnelVote;
    private final LevelChangeRequest levelChangeRequest;
    private UUID clientId;

    public ActionRequestDto(LevelChangeRequest levelChangeRequest) {
        this.levelChangeRequest = levelChangeRequest;
        this.direction = null;
        this.tunnelVote = null;
    }

    public ActionRequestDto(Direction direction) {
        this.direction = direction;
        this.tunnelVote = null;
        this.levelChangeRequest = null;
    }

    public ActionRequestDto(Integer tunnelVote) {
        this.tunnelVote = tunnelVote;
        this.direction = null;
        this.levelChangeRequest = null;
    }

    @JsonCreator
    public ActionRequestDto(
            @JsonProperty("direction") Direction direction,
            @JsonProperty("tunnelVote") Integer tunnelVote,
            @JsonProperty("clientId") UUID clientId,
            @JsonProperty("levelChangeRequest") LevelChangeRequest levelChangeRequest) {
        this.direction = direction;
        this.tunnelVote = tunnelVote;
        this.clientId = clientId;
        this.levelChangeRequest = levelChangeRequest;
    }
}
