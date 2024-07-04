package at.ac.tuwien.foop.server.mapper;

import at.ac.tuwien.foop.network.dto.CatDto;
import at.ac.tuwien.foop.network.dto.GameStateDto;
import at.ac.tuwien.foop.network.dto.GameStatusDto;
import at.ac.tuwien.foop.network.dto.MouseDto;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.GameStatus;

import java.time.Duration;
import java.time.LocalDateTime;

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
        return new GameStateDto(gameState.getGameField().getBounds(), Duration.between(gameState.getGameStart(), LocalDateTime.now()), mice, cats, mapEnum(gameState.getGameStatus()));
    }

    private static GameStatusDto mapEnum(GameStatus status) {
        return GameStatusDto.valueOf(status.name());
    }

}
