package at.ac.tuwien.foop.server.game.state;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class GameEntity {
    private double[] pos;
    private double size;

    public GameEntity() {}

    public GameEntity(double size, double[] pos) {
        this.size = size;
        this.pos = pos;
    }

    public boolean intersects(GameEntity other) {
        double dx = this.pos[0] - other.pos[0];
        double dy = this.pos[1] - other.pos[1];
        double distance = Math.sqrt(dx * dx + dy * dy);
        double radiusSum = (this.size / 2) + (other.size / 2);
        return distance < radiusSum;
    }
}
