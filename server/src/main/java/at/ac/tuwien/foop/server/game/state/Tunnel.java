package at.ac.tuwien.foop.server.game.state;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Tunnel {
    private final LinkedList<TunnelNode> tunnelNodeList;
}
