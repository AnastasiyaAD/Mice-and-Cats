package at.ac.tuwien.foop.server.game.state;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Tunnel {
    private final LinkedList<TunnelNode> tunnelNodeList;

    /**
     * Expects x and y positions converted to the 15x16 game field grid coordinate system
     */
    public boolean isPosWithinDoor(double x, double y) {
        return tunnelNodeList.stream().filter(TunnelNode::isDoorNode).anyMatch(door -> {
            var doorX = door.position()[0];
            var doorY = door.position()[1];
            return x >= doorX && x <= doorX && y >= doorY && y <= doorY;
        });
    }

    public boolean isPosWithinTunnel(double x, double y) {
        return tunnelNodeList.stream().anyMatch(node -> {
            var nodeX = node.position()[0];
            var nodeY = node.position()[1];
            return x >= nodeX && x <= nodeX && y >= nodeY && y <= nodeY;
        });
    }
}
