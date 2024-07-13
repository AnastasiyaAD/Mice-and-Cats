package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.state.Cat;
import at.ac.tuwien.foop.server.game.state.GameState;

import java.util.List;

public interface ICatManager {
    List<Cat> spawnCats(Configuration configuration, GameState gameState);
    void updateCats(GameState gameState);
}
