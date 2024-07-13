package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.cats.IServerCatState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Optional;
@EqualsAndHashCode
@Data
public abstract class Cat implements IServerCatState {
    protected double[] pos;
    protected final Configuration configuration;

    public Cat(Configuration configuration, double[] position) {
        this.pos = position;
        this.configuration = configuration;
    }

    @Override
    public double[] getPos() {
        return this.pos;
    }

    @Override
    public Optional<Mouse> move(GameState gameState) {
        var seenMice = gameState.getMice().values().stream().filter(mouse -> mouse.getCurrentLevel() == 0).toList();
        var nearestMouse = findNearest(seenMice, pos);

        if (nearestMouse.isPresent()) {
            moveTowards(nearestMouse.get().getPos());
            return intersects(nearestMouse.get());
        } else {
            moveTowards(getDefaultPosition());
            return Optional.empty();
        }
    }

    protected void moveTowards(double[] targetPos) {
        double speedPerTick = ((double) configuration.catSpeed() / 5) / configuration.tickRate();
        this.pos = computeMoveToMouse(targetPos, this.pos, speedPerTick);
    }

    protected abstract double[] getDefaultPosition();

    protected Optional<Mouse> intersects(Mouse mouse) {
        double dx = this.pos[0] - mouse.getPos()[0];
        double dy = this.pos[1] - mouse.getPos()[1];
        double distance = Math.sqrt(dx * dx + dy * dy);
        double radiusSum = (1.44 / 2) + (0.8 / 2);
        return (distance < radiusSum) ? Optional.of(mouse) : Optional.empty();
    }

    protected double[] computeMoveToMouse(double[] targetPos, double[] catPos, double distance) {
        double dx = targetPos[0] - catPos[0];
        double dy = targetPos[1] - catPos[1];
        double length = Math.sqrt(dx * dx + dy * dy);

        if (length == 0) return catPos; // Avoid division by zero

        double dirX = dx / length;
        double dirY = dy / length;
        double moveX = dirX * distance;
        double moveY = dirY * distance;

        double[] newCatPos = {catPos[0] + (moveX / 3), catPos[1] + (moveY / 3)};
        return Double.isNaN(newCatPos[0]) || Double.isNaN(newCatPos[1]) ? targetPos : newCatPos;
    }

    protected Optional<Mouse> findNearest(List<Mouse> mice, double[] position) {
        if (mice == null || mice.isEmpty() || position == null || position.length < 2) {
            return Optional.empty();
        }

        return mice.stream()
                .min((mouse1, mouse2) -> {
                    double dist1 = Math.sqrt(Math.pow(mouse1.getPos()[0] - position[0], 2) + Math.pow(mouse1.getPos()[1] - position[1], 2));
                    double dist2 = Math.sqrt(Math.pow(mouse2.getPos()[0] - position[0], 2) + Math.pow(mouse2.getPos()[1] - position[1], 2));
                    return Double.compare(dist1, dist2);
                });
    }
}