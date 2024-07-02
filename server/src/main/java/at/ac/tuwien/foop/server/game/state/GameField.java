package at.ac.tuwien.foop.server.game.state;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static at.ac.tuwien.foop.server.game.state.NodeType.*;

/**
 * (x, y)<br>
 * Top-left is origin (0,0)<br>
 * x-axis moves horizontally<br>
 * y-axis moves vertically<br>
 * e.g. (5,0) is to the right of the origin<br>
 * (0,5) is below the origin
 */
@Getter
public class GameField {
    private final int[] bounds = new int[]{14, 15};
    private final List<Tunnel> tunnels = new ArrayList<>();

    public GameField() {
        // Tunnel 1
        var tunnel2Nodes = new LinkedList<TunnelNode>();
        tunnel2Nodes.add(new TunnelNode(new int[]{7,1}, TILE));
        tunnel2Nodes.add(new TunnelNode(new int[]{8,1}, TILE));
        tunnel2Nodes.add(new TunnelNode(new int[]{6,2}, DOOR));
        tunnel2Nodes.add(new TunnelNode(new int[]{7,2}, TILE));
        tunnel2Nodes.add(new TunnelNode(new int[]{8,2}, TILE));
        tunnel2Nodes.add(new TunnelNode(new int[]{8,3}, TILE));
        tunnel2Nodes.add(new TunnelNode(new int[]{8,4}, DOOR));
        var tunnel1 = new Tunnel(1, tunnel2Nodes);
        // ----

        tunnels.add(tunnel1);
    }
}
