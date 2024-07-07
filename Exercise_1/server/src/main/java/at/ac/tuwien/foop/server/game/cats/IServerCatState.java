package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.state.GameState;

import java.util.Optional;

public interface IServerCatState {
    double[] getPos();
    Optional<String> move(GameState gameState);
}
