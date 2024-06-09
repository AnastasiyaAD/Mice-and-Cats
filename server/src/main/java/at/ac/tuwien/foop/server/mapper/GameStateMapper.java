package at.ac.tuwien.foop.server.mapper;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import at.ac.tuwien.foop.server.game.state.GameState;

public class GameStateMapper {

    public static GameStateDto toDto(GameState gameState) {
        var dto = new GameStateDto(gameState.getGameStart());
        return dto;
    }
}
