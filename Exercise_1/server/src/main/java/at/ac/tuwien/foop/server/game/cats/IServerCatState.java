package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.Mouse;

import java.util.Optional;

public interface IServerCatState {
    double[] getPos();
    Optional<Mouse> move(GameState gameState);
}
