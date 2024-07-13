package at.ac.tuwien.foop.server.game.state;

import lombok.Getter;

/**
 * Represents a node within a tunnel, which can have connections to the north, east, south, and west.
 */
@Getter
public final class TunnelNode {
    private final int[] position;
    private final NodeType type;
    private boolean northConnection = false;
    private boolean eastConnection = false;
    private boolean southConnection = false;
    private boolean westConnection = false;

    /**
     * Constructs a TunnelNode with the specified position and type.
     *
     * @param position The position of the node in the form of an integer array [x, y].
     * @param type     The type of the node, either DOOR or TILE.
     */
    public TunnelNode(int[] position, NodeType type) {
        this.position = position;
        this.type = type;
    }

    /**
     * Sets the north connection of this node and returns the node.
     *
     * @return This TunnelNode with the north connection set.
     */
    public TunnelNode northConnection() {
        this.northConnection = true;
        return this;
    }

    /**
     * Sets the east connection of this node and returns the node.
     *
     * @return This TunnelNode with the east connection set.
     */
    public TunnelNode eastConnection() {
        this.eastConnection = true;
        return this;
    }

    /**
     * Sets the south connection of this node and returns the node.
     *
     * @return This TunnelNode with the south connection set.
     */
    public TunnelNode southConnection() {
        this.southConnection = true;
        return this;
    }

    /**
     * Sets the west connection of this node and returns the node.
     *
     * @return This TunnelNode with the west connection set.
     */
    public TunnelNode westConnection() {
        this.westConnection = true;
        return this;
    }

    /**
     * Checks if this node is a door node.
     *
     * @return true if this node is a door node, false otherwise.
     */
    public boolean isDoorNode() {
        return this.type == NodeType.DOOR;
    }
}
