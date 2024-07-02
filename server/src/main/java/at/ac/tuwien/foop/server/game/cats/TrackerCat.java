package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.Mouse;

public class TrackerCat implements IServerCatState {
    double[] position = new double[2];

    public TrackerCat(double[] position) {
        this.position = position;
    }

    @Override
    public double[] getPos() {
        return this.position;
    }

    @Override
    public void move(GameState gameState) {
        // Directly heads to the nearest mouse, or defaults to the nearest tunnel if no mouse is present
    }

    private Mouse findNearestMouse() {
        return null;
    }
}
