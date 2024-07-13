package at.ac.tuwien.foop.server.game.state;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a mouse in the game, with a username, position, current level, and tunnel vote.
 */
@EqualsAndHashCode
@Data
public class Mouse {
    private final String username;
    private double[] pos = new double[]{1, 1};
    private int currentLevel;
    private Integer tunnelVote;

    /**
     * Constructs a Mouse object with the specified username.
     *
     * @param username The username of the mouse.
     */
    public Mouse(String username) {
        this.username = username;
        this.currentLevel = 0;
    }

    /**
     * Moves the mouse by the specified x and y amounts.
     *
     * @param x The amount to move the mouse in the x direction.
     * @param y The amount to move the mouse in the y direction.
     */
    public void move(double x, double y) {
        this.pos[0] += x;
        this.pos[1] += y;
    }

    /**
     * Sets the position of the mouse to the specified x and y coordinates.
     *
     * @param x The x coordinate to set the mouse's position.
     * @param y The y coordinate to set the mouse's position.
     */
    public void setPosition(double x, double y) {
        this.pos[0] = x;
        this.pos[1] = y;
    }
}
