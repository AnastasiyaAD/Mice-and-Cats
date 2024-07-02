package at.ac.tuwien.foop.server.game.state;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class Mouse {
    private final String username;
    private double[] pos = new double[]{0,0};
    private int currentLevel;
    private Integer tunnelVote;

    public Mouse(String username) {
        this.username = username;
        this.currentLevel = 0;
    }

    public void move(double x, double y) {
        this.pos[0] += x;
        this.pos[1] += y;
    }

    public void setPosition(double x, double y) {
        this.pos[0] = x;
        this.pos[1] = y;
    }
}
