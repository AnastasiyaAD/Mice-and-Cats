package at.ac.tuwien.foop.server.game.state;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.Random;

@Data
@AllArgsConstructor
public class Tunnel {
    private final LinkedList<TunnelNode> tunnelNodeList;

    /**
     * Expects x and y positions converted to the 15x16 game field grid coordinate system
     */
    public boolean isPosWithinDoor(double x, double y, double characterSize) {
        return tunnelNodeList.stream().filter(TunnelNode::isDoorNode).anyMatch(node -> posWithinNode(x, y, characterSize, node, false));
    }


    public boolean isPosWithinTunnel(double x, double y, double characterSize) {
        return tunnelNodeList.stream().anyMatch(node -> posWithinNode(x, y, characterSize, node, true));
    }

    private static boolean posWithinNode(double x, double y, double characterSize, TunnelNode node, boolean accountForConnections) {
        var nodeX = node.getPosition()[0];
        var nodeY = node.getPosition()[1];
        double characterSizeHalf = characterSize / 2;
        var westBoundsCheck = x - (node.isWestConnection() && accountForConnections ? 0 : characterSizeHalf) >= nodeX;
        var eastBoundsCheck = x + (node.isEastConnection() && accountForConnections ? 0 : characterSizeHalf) <= nodeX + 1;
        var northBoundsCheck = y - (node.isNorthConnection() && accountForConnections ? 0 : characterSizeHalf) >= nodeY;
        var southBoundsCheck = y + (node.isSouthConnection() && accountForConnections ? 0 : characterSizeHalf) <= nodeY + 1;
        return westBoundsCheck && eastBoundsCheck && northBoundsCheck && southBoundsCheck;
    }

    public TunnelNode getRandomNonDoorNode() {
        var nonDoorNodes = tunnelNodeList.stream().filter(TunnelNode::isDoorNode).toList();
        var random = new Random();
        return nonDoorNodes.get(random.nextInt(nonDoorNodes.size()));
    }
}
