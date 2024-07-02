package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.state.GameState;

public interface IServerCatState {
    double[] getPos();
    void move(GameState gameState);
}
