package at.ac.tuwien.foop.network.dto;

import java.time.Duration;
import java.util.List;

public record GameStateDto(Duration timeRemaining, List<MouseDto> mice, GameStatusDto status) {
}
