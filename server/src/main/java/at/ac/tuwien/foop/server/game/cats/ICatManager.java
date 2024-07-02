package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.state.GameState;

public interface ICatManager {
    void spawnCats(GameState gameState);
    void updateCats(GameState gameState);
}
