package at.ac.tuwien.foop.server.game.state;

/**
 * Represents a position within a tunnel, consisting of a level and a tunnel node.
 *
 * @param level The level of the tunnel.
 * @param node  The specific tunnel node at the given level.
 */
public record TunnelPosition(int level, TunnelNode node) { }
