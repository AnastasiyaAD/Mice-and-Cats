package at.ac.tuwien.foop.server.mapper;

import at.ac.tuwien.foop.network.dto.*;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.GameStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GameStateMapper {

    public static GameStateDto toDto(GameState gameState) {
        var mice = gameState.getMice().entrySet().stream()
                .map(entry -> new MouseDto(
                        entry.getValue().getPos(),
                        entry.getKey(),
                        entry.getValue().getUsername(),
                        entry.getValue().getCurrentLevel(),
                        entry.getValue().getTunnelVote())
                ).toList();
        var cats = gameState.getCats().stream().map(cat -> new CatDto(cat.getPos())).toList();
        var timeElapsed = Duration.between(gameState.getGameStart(), LocalDateTime.now());
        var seenCats = gameState.getCatSnapshots().entrySet();
        HashMap<Integer, CatSnapshotDto> catSnapshotDtoHashMap = new HashMap<>();
        for (var entry: seenCats) {
            var value = entry.getValue();
            var key = entry.getKey();

            catSnapshotDtoHashMap.put(key, new CatSnapshotDto(value.getCatPositions()));
        }
        return new GameStateDto(gameState.getGameField().getBounds(),
                gameState.getGameDuration().minus(timeElapsed),
                mice,
                cats,
                catSnapshotDtoHashMap,
                mapEnum(gameState.getGameStatus()),
                gameState.getCatSnapshots().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new CatSnapshotDto(entry.getValue().getCatPositions()))));

    }

    private static GameStatusDto mapEnum(GameStatus status) {
        return GameStatusDto.valueOf(status.name());
    }

}
