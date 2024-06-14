package at.ac.tuwien.foop.server.game.state;

import lombok.AllArgsConstructor;

import java.util.LinkedList;

@AllArgsConstructor
public class Tunnel {
    private final LinkedList<TunnelNode> tunnelNodeList;
}
