package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.state.Cat;

public class TrackerCat extends Cat {

    public TrackerCat(Configuration configuration, double[] position) {
        super(configuration, position);
    }

    @Override
    protected double[] getDefaultPosition() {
        return this.pos; // TrackerCat has no specific default position
    }
}