package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.cats.IServerCatState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Optional;

/**
 * Abstract class representing a Cat in the game.
 * A Cat can move towards mice and interact with them.
 *
 * Object invariants:
 * - The cat's position array (pos) must always have a length of 2.
 * - The configuration object must not be null.
 */
@EqualsAndHashCode
@Data
public abstract class Cat implements IServerCatState {
    protected double[] pos;
    protected final Configuration configuration;

    /**
     * Constructor for the Cat class.
     *
     * @param configuration the game configuration settings
     * @param position the initial position of the cat
     *
     * @pre configuration != null
     * @pre position != null && position.length == 2
     */
    public Cat(Configuration configuration, double[] position) {
        this.pos = position;
        this.configuration = configuration;
    }

    /**
     * Returns the current position of the cat.
     *
     * @return the current position as a double array
     */
    @Override
    public double[] getPos() {
        return this.pos;
    }

    /**
     * Moves the cat within the game state. If a mouse is nearby, moves towards it.
     * Otherwise, moves towards a default position.
     *
     * @param gameState the current game state
     * @return an Optional containing a Mouse if the cat intersects with one, otherwise empty
     */
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

    /**
     * Moves the cat towards a target position.
     *
     * @param targetPos the target position
     *
     * @pre targetPos != null && targetPos.length == 2
     */
    protected void moveTowards(double[] targetPos) {
        double speedPerTick = ((double) configuration.catSpeed() / 5) / configuration.tickRate();
        this.pos = computeMoveToMouse(targetPos, this.pos, speedPerTick);
    }

    /**
     * Abstract method to get the default position of the cat.
     *
     * @return the default position as a double array
     */
    protected abstract double[] getDefaultPosition();

    /**
     * Checks if the cat intersects with a mouse.
     *
     * @param mouse the mouse to check intersection with
     * @return an Optional containing the mouse if intersecting, otherwise empty
     *
     * @pre mouse != null
     * @post the distance between the cat and the mouse is less than the sum of their radii
     */
    protected Optional<Mouse> intersects(Mouse mouse) {
        double dx = this.pos[0] - mouse.getPos()[0];
        double dy = this.pos[1] - mouse.getPos()[1];
        double distance = Math.sqrt(dx * dx + dy * dy);
        double radiusSum = (1.44 / 2) + (0.8 / 2);
        return (distance < radiusSum) ? Optional.of(mouse) : Optional.empty();
    }

    /**
     * Computes the new position of the cat when moving towards a mouse.
     *
     * @param targetPos the target position of the mouse
     * @param catPos the current position of the cat
     * @param distance the distance the cat can move
     * @return the new position of the cat
     *
     * @pre targetPos != null && targetPos.length == 2
     * @pre catPos != null && catPos.length == 2
     */
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

    /**
     * Finds the nearest mouse to the cat's current position.
     *
     * @param mice the list of mice
     * @param position the current position of the cat
     * @return an Optional containing the nearest mouse if present, otherwise empty
     *
     * @pre mice != null
     * @pre position != null && position.length == 2
     */
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
