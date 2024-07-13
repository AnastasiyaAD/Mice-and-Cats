package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.state.Cat;
import at.ac.tuwien.foop.server.game.state.Mouse;

import java.util.List;
import java.util.Optional;

public class TunnelCamperCat extends Cat {
    private final double[] tunnelToGuard;

    public TunnelCamperCat(Configuration configuration, double[] position) {
        super(configuration, position);
        this.tunnelToGuard = position.clone();
    }

    @Override
    protected double[] getDefaultPosition() {
        return this.tunnelToGuard;
    }

    @Override
    protected Optional<Mouse> findNearest(List<Mouse> mice, double[] position) {
        return super.findNearest(mice, position)
                .filter(mouse -> Math.sqrt(Math.pow(mouse.getPos()[0] - position[0], 2) + Math.pow(mouse.getPos()[1] - position[1], 2)) <= 3);
    }
}