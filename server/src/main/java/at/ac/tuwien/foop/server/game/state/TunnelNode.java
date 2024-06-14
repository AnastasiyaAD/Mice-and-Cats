package at.ac.tuwien.foop.server.game.state;

public record TunnelNode(int[] position, NodeType type) {
    enum NodeType {
        DOOR,
        TILE;
    }
}
