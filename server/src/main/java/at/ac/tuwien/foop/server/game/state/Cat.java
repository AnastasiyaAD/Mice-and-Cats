package at.ac.tuwien.foop.server.game.state;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class Cat {
    private double[] pos = new double[]{0, 0};

    public void move(double x, double y) {
        this.pos[0] += x;
        this.pos[1] += y;
    }
}
