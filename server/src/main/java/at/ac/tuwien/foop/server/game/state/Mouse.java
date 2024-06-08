package at.ac.tuwien.foop.server.game.state;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
@Data
public class Mouse {
    private final UUID clientId;
    private final String username;
    private double[] pos = new double[]{0,0};

    public Mouse(UUID clientId, String username) {
        this.clientId = clientId;
        this.username = username;
    }

    public void changePosX(double x) {
        this.pos[0] += x;
    }

    public void changePosY(double y) {
        this.pos[1] += y;
    }
}
