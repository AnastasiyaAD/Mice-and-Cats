package at.ac.tuwien.foop.server.game.state;

import lombok.Getter;

import java.util.*;

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
    private final double mouseSize = 0.8;
    private final double catSize = 1.44;
    private final Map<Integer, Tunnel> tunnels = new HashMap<>();

    public GameField() {
        // Tunnel 1
        var tunnel1Nodes = new LinkedList<TunnelNode>();
        tunnel1Nodes.add(new TunnelNode(new int[]{7,1}, TILE).eastConnection().southConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,1}, TILE).southConnection().westConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{6,2}, DOOR).eastConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{7,2}, TILE).northConnection().eastConnection().westConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,2}, TILE).northConnection().southConnection().westConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,3}, TILE).southConnection().northConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,4}, DOOR).northConnection());
        var tunnel1 = new Tunnel(tunnel1Nodes);
        // ----

        tunnels.put(1, tunnel1);
    }
}
