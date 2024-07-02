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
    private final int[] bounds = new int[]{1500, 1500};
    private final List<Tunnel> tunnels = new ArrayList<>();

    public GameField() {
        // Tunnel 2
        var tunnel2Nodes = new LinkedList<TunnelNode>();
        tunnel2Nodes.addFirst(new TunnelNode(new int[]{5,0}, DOOR));
        tunnel2Nodes.add(new TunnelNode(new int[]{6,0}, TILE));
        tunnel2Nodes.add(new TunnelNode(new int[]{7,0}, DOOR));
        var tunnel2 = new Tunnel(tunnel2Nodes);
        // ----

        tunnels.add(tunnel2);
    }
}
