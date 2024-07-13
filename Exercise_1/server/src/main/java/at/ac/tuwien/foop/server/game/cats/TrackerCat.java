package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.Configuration;

public class TrackerCat extends AbstractCat {

    public TrackerCat(Configuration configuration, double[] position) {
        super(configuration, position);
    }

    @Override
    protected double[] getDefaultPosition() {
        return this.position; // TrackerCat has no specific default position
    }
}