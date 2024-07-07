package at.ac.tuwien.foop.network.dto;

import java.time.Duration;
import java.util.List;

public record GameStateDto(int[] gameField, Duration timeRemaining, List<MouseDto> mice, List<CatDto> cats, GameStatusDto status) {
}
