package at.ac.tuwien.foop.network.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ActionRequestDto {

    private final Direction direction;
    private UUID clientId;

    public ActionRequestDto(Direction direction) {
        this.direction = direction;
    }
}
