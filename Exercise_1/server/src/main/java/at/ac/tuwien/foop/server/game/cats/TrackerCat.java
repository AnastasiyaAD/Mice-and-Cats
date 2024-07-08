package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.state.GameState;
import at.ac.tuwien.foop.server.game.state.Mouse;

import java.util.List;
import java.util.Optional;

public class TrackerCat implements IServerCatState {
    private double[] position = new double[2];
    private final Configuration configuration;
    public TrackerCat(Configuration configuration, double[] position) {
        this.position = position;
        this.configuration = configuration;
    }

    @Override
    public double[] getPos() {
        return this.position;
    }

    @Override
    public Optional<Mouse> move(GameState gameState) {
        // Directly heads to the nearest mouse, or defaults to the nearest tunnel if no mouse is present
        // TODO: unify mouse catch logic
        var seenMice = gameState.getMice().values().stream().filter(mouse -> mouse.getCurrentLevel() == 0).toList();
        var nearestMouse = findNearest(seenMice, position);

        if (nearestMouse.isPresent()) {
            var foundMouse = nearestMouse.get();
            double speedPerTick = ((double) configuration.catSpeed() / 10) / configuration.tickRate();
            var nextPos = computeMoveToMouse(foundMouse.getPos(), this.position, speedPerTick);
            this.position = nextPos;
        }
        if (nearestMouse.isPresent()) {
            return intersects(nearestMouse.get());
        } else {
            return Optional.empty();
        }
    }

    private Optional<Mouse> intersects(Mouse mouse) {
        double dx = this.position[0] - mouse.getPos()[0];
        double dy = this.position[1] - mouse.getPos()[1];
        double distance = Math.sqrt(dx * dx + dy * dy);
        double radiusSum = (1.44 / 2) + (0.8 / 2);
        if (distance < radiusSum) {
            return Optional.of(mouse);
        } else {
            return Optional.empty();
        }
    }

    private double[] computeMoveToMouse(double[] mousePos, double[] catPos, double distance) {
        double dx = mousePos[0] - catPos[0];
        double dy = mousePos[1] - catPos[1];

        double arg = dx * dx + dy * dy;
        double length = Math.sqrt(arg);

        // Normalize the direction vector
        double dirX = dx / length;
        double dirY = dy / length;

        // Scale the direction vector by the given distance
        double moveX = dirX * distance;
        double moveY = dirY * distance;

        // Compute the new position
        double[] newCatPos = { catPos[0] + (moveX / 3), catPos[1] + (moveY / 3) };
        return newCatPos;
    }

    private Optional<Mouse> findNearest(List<Mouse> mice, double[] position) {
        if (mice == null || mice.isEmpty() || position == null || position.length < 2) {
            return Optional.empty();
        }

        Mouse nearestMouse = null;
        double minDistance = Double.MAX_VALUE;
        double posX = position[0];
        double posY = position[1];

        for (Mouse mouse : mice) {
            double distance = Math.sqrt(Math.pow(mouse.getPos()[0] - posX, 2) + Math.pow(mouse.getPos()[1] - posY, 2));
            if (distance < minDistance) {
                minDistance = distance;
                nearestMouse = mouse;
            }
        }

        return Optional.ofNullable(nearestMouse);
    }
}
