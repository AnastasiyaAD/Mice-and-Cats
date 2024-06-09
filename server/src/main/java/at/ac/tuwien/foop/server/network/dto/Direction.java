package at.ac.tuwien.foop.server.network.dto;

import lombok.Getter;

@Getter
public enum Direction {
    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1, -1),
    WEST(0, -1),
    NORTH_WEST(-1, -1);

    private final double dirX;
    private final double dirY;

    Direction(int dx, int dy) {
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = dx / magnitude;
        double normalizedDy = dy / magnitude;
        this.dirX = normalizedDx;
        this.dirY = normalizedDy;
    }
}
