package at.ac.tuwien.foop.server.game.state;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.Random;

/**
 * Represents a tunnel in the game, consisting of a list of tunnel nodes.
 */
@Data
@AllArgsConstructor
public class Tunnel {
    private final LinkedList<TunnelNode> tunnelNodeList;

    /**
     * Checks if the given position is within any door node of the tunnel.
     *
     * @param x             The x-coordinate of the position.
     * @param y             The y-coordinate of the position.
     * @param characterSize The size of the character.
     * @return true if the position is within a door node, false otherwise.
     */
    public boolean isPosWithinDoor(double x, double y, double characterSize) {
        return tunnelNodeList.stream().filter(TunnelNode::isDoorNode).anyMatch(node -> posWithinNode(x, y, characterSize, node, false));
    }

    /**
     * Checks if the given position is within any node of the tunnel.
     *
     * @param x             The x-coordinate of the position.
     * @param y             The y-coordinate of the position.
     * @param characterSize The size of the character.
     * @return true if the position is within a tunnel node, false otherwise.
     */
    public boolean isPosWithinTunnel(double x, double y, double characterSize) {
        return tunnelNodeList.stream().anyMatch(node -> posWithinNode(x, y, characterSize, node, true));
    }

    /**
     * Helper method to determine if a position is within a specific tunnel node.
     *
     * @param x                     The x-coordinate of the position.
     * @param y                     The y-coordinate of the position.
     * @param characterSize         The size of the character.
     * @param node                  The tunnel node to check against.
     * @param accountForConnections Whether to account for node connections when checking bounds.
     * @return true if the position is within the node, false otherwise.
     */
    private static boolean posWithinNode(double x, double y, double characterSize, TunnelNode node, boolean accountForConnections) {
        var nodeX = node.getPosition()[0];
        var nodeY = node.getPosition()[1];
        double characterSizeHalf = characterSize / 2;
        var westBoundsCheck = x - (node.isWestConnection() && accountForConnections ? 0 : characterSizeHalf) >= nodeX - 0.1;
        var eastBoundsCheck = x + (node.isEastConnection() && accountForConnections ? 0 : characterSizeHalf) <= nodeX + 1.1;
        var northBoundsCheck = y - (node.isNorthConnection() && accountForConnections ? 0 : characterSizeHalf) >= nodeY - 0.1;
        var southBoundsCheck = y + (node.isSouthConnection() && accountForConnections ? 0 : characterSizeHalf) <= nodeY + 1.1;
        return westBoundsCheck && eastBoundsCheck && northBoundsCheck && southBoundsCheck;
    }

    /**
     * Returns a random tunnel node that is not a door.
     *
     * @return A random non-door tunnel node.
     */
    public TunnelNode getRandomNonDoorNode() {
        var nonDoorNodes = tunnelNodeList.stream().filter(x -> !x.isDoorNode()).toList();
        var random = new Random();
        return nonDoorNodes.get(random.nextInt(nonDoorNodes.size()));
    }
}
