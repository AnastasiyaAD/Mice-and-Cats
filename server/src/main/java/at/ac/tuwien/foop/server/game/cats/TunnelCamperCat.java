package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.state.GameState;

public class TunnelCamperCat implements IServerCatState {
    private int ticksWaitedOnTunnel = 0;

    @Override
    public double[] getPos() {
        return new double[0];
    }

    @Override
    public void move(GameState gameState) {
        // Camp at some tunnel for x ticks, chase mice if the are within x distance
        var tunnels = gameState.getGameField().getTunnels();
        for (var tunnel : tunnels) {

        }
    }
}
