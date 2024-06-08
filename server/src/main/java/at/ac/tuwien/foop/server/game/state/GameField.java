package at.ac.tuwien.foop.server.game.state;

/**
 * (x, y)<br>
 * Top-left is origin (0,0)<br>
 * x-axis moves horizontally<br>
 * y-axis moves vertically<br>
 * e.g. (5,0) is to the right of the origin<br>
 * (0,5) is below the origin
 */
public class GameField {
    private final int[] bounds = new int[]{800, 600};
}
