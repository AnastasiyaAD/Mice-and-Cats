package at.ac.tuwien.foop.server.game.state;


import lombok.Getter;

@Getter
public final class TunnelNode {
    private final int[] position;
    private final NodeType type;
    private boolean northConnection = false;
    private boolean eastConnection = false;
    private boolean southConnection = false;
    private boolean westConnection = false;

    public TunnelNode(int[] position, NodeType type) {
        this.position = position;
        this.type = type;
    }

    public TunnelNode northConnection() {
        this.northConnection = true;
        return this;
    }

    public TunnelNode eastConnection() {
        this.eastConnection = true;
        return this;
    }

    public TunnelNode southConnection() {
        this.southConnection = true;
        return this;
    }

    public TunnelNode westConnection() {
        this.westConnection = true;
        return this;
    }

    public boolean isDoorNode() {
        return this.type == NodeType.DOOR;
    }
}
