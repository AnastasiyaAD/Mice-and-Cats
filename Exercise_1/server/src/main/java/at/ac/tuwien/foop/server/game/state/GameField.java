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
        var tunnel2Nodes = new LinkedList<TunnelNode>();
        var tunnel3Nodes = new LinkedList<TunnelNode>();
        var tunnel4Nodes = new LinkedList<TunnelNode>();
        var tunnel5Nodes = new LinkedList<TunnelNode>();

        tunnel1Nodes.add(new TunnelNode(new int[]{7,1}, TILE).eastConnection().southConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,1}, TILE).southConnection().westConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{6,2}, DOOR).eastConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{7,2}, TILE).northConnection().eastConnection().westConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,2}, TILE).northConnection().southConnection().westConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,3}, TILE).southConnection().northConnection());
        tunnel1Nodes.add(new TunnelNode(new int[]{8,4}, DOOR).northConnection());
        var tunnel1 = new Tunnel(tunnel1Nodes);

        // Tunnel 2
        tunnel2Nodes.add(new TunnelNode(new int[]{10,1}, DOOR).eastConnection());
        tunnel2Nodes.add(new TunnelNode(new int[]{11,1}, TILE).eastConnection().westConnection());
        tunnel2Nodes.add(new TunnelNode(new int[]{12,1}, TILE).eastConnection().westConnection());
        tunnel2Nodes.add(new TunnelNode(new int[]{13,1}, TILE).southConnection().westConnection());
        tunnel2Nodes.add(new TunnelNode(new int[]{13,2}, TILE).southConnection().northConnection());
        tunnel2Nodes.add(new TunnelNode(new int[]{13,3}, DOOR).northConnection());
        var tunnel2 = new Tunnel(tunnel2Nodes);

        // Tunnel 3
        tunnel3Nodes.add(new TunnelNode(new int[]{5,6}, DOOR).southConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{5,7}, TILE).northConnection().eastConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{6,7}, TILE).westConnection().eastConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{7,7}, TILE).westConnection().eastConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{8,7}, TILE).southConnection().westConnection().eastConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{8,8}, TILE).southConnection().northConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{8,9}, DOOR).northConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{9,7}, TILE).westConnection().eastConnection());
        tunnel3Nodes.add(new TunnelNode(new int[]{10,7}, DOOR).westConnection());
        var tunnel3 = new Tunnel(tunnel3Nodes);

        // Tunnel 4
        tunnel4Nodes.add(new TunnelNode(new int[]{1,13}, DOOR).northConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{1,12}, TILE).southConnection().eastConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{2,12}, TILE).eastConnection().westConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{3,12}, TILE).westConnection().northConnection().eastConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{4,12}, TILE).westConnection().northConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{3,11}, TILE).northConnection().southConnection().eastConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{4,11}, TILE).southConnection().westConnection().eastConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{5,11}, DOOR).westConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{2,10}, DOOR).eastConnection().northConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{2,9}, TILE).southConnection().eastConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{3,9}, TILE).westConnection().southConnection());
        tunnel4Nodes.add(new TunnelNode(new int[]{3,10}, TILE).southConnection().westConnection().northConnection());
        var tunnel4 = new Tunnel(tunnel4Nodes);

        // Tunnel 5
        tunnel5Nodes.add(new TunnelNode(new int[]{7,15}, DOOR).eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{8,15}, TILE).westConnection().eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{9,15}, TILE).westConnection().eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{10,15}, TILE).westConnection().eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{11,15}, TILE).westConnection().eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{12,15}, TILE).westConnection().eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{13,15}, TILE).eastConnection().westConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{14,15}, TILE).westConnection().northConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{14,14}, TILE).southConnection().northConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{14,13}, TILE).southConnection().northConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{14,12}, TILE).southConnection().northConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{14,11}, TILE).southConnection().westConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{13,11}, TILE).westConnection().eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{12,11}, TILE).southConnection().eastConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{12,12}, TILE).northConnection().southConnection());
        tunnel5Nodes.add(new TunnelNode(new int[]{12,13}, DOOR).northConnection());
        var tunnel5 = new Tunnel(tunnel5Nodes);


        tunnels.put(1, tunnel1);
        tunnels.put(2, tunnel2);
        tunnels.put(3, tunnel3);
        tunnels.put(4, tunnel4);
        tunnels.put(5, tunnel5);
    }
}
