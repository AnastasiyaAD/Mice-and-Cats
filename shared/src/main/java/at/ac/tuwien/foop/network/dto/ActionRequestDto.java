package at.ac.tuwien.foop.network.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ActionRequestDto {

    private final ActionTypeEnum type;
    private final Direction direction;
    private final Integer tunnelVote;
    private final LevelChangeRequest levelChangeRequest;
    private UUID clientId;

    public enum ActionTypeEnum{
        DIRECTION,
        TUNNEL_VOTE,
        LEVEL_CHANGE
    }

    public ActionRequestDto(LevelChangeRequest levelChangeRequest) {
        this.levelChangeRequest = levelChangeRequest;
        this.type = ActionTypeEnum.LEVEL_CHANGE;
        this.direction = null;
        this.tunnelVote = null;
    }

    public ActionRequestDto(Direction direction) {
        this.type = ActionTypeEnum.DIRECTION;
        this.direction = direction;
        this.tunnelVote = null;
        this.levelChangeRequest = null;
    }

    public ActionRequestDto(Integer tunnelVote) {
        this.type = ActionTypeEnum.TUNNEL_VOTE;
        this.tunnelVote = tunnelVote;
        this.direction = null;
        this.levelChangeRequest = null;
    }

    @JsonCreator
    public ActionRequestDto(
            @JsonProperty("direction") Direction direction,
            @JsonProperty("type") ActionTypeEnum type,
            @JsonProperty("tunnelVote") Integer tunnelVote,
            @JsonProperty("clientId") UUID clientId,
            @JsonProperty("levelChangeRequest") LevelChangeRequest levelChangeRequest) {
        this.type = type;
        this.direction = direction;
        this.tunnelVote = tunnelVote;
        this.clientId = clientId;
        this.levelChangeRequest = levelChangeRequest;
    }
}
