package at.ac.tuwien.foop.server.game.state;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class Mouse {
    private final String username;
    private double[] pos = new double[]{0,0};

    public Mouse(String username) {
        this.username = username;
    }

    public void move(double x, double y) {
        this.pos[0] += x;
        this.pos[1] += y;
    }
}
