package at.ac.tuwien.foop.network.dto;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public record GameStateDto(int[] gameField, Duration timeRemaining, List<MouseDto> mice, List<CatDto> cats, GameStatusDto status, Map<Integer, CatSnapshotDto> catSnapshots) {
}
