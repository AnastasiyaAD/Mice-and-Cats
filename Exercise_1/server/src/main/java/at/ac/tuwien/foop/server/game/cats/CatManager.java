package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.state.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class CatManager implements ICatManager {
    private final List<IServerCatState> cats = new ArrayList<IServerCatState>();
    private List<int[]> getTunnelEntranceCoordinates(GameState gameState) {
        var tunnels = gameState.getGameField().getTunnels().values();
        return tunnels
                .stream()
                .flatMap(
                        t -> t.getTunnelNodeList()
                                .stream()
                                .filter(TunnelNode::isDoorNode)
                                .map(TunnelNode::getPosition))
                .toList();
    }

    private List<Mouse> getMiceOnSurface(GameState gameState) {
        var mice = gameState.getMice().values();
        return mice.stream().filter(mouse -> mouse.getCurrentLevel() == -1).toList();
    }

    @Override
    public void spawnCats(Configuration configuration, GameState gameState) {
        var entrances = getTunnelEntranceCoordinates(gameState);
        // Random number generator
        Random rand = new Random();

        for (int i = 0; i < configuration.trackerCats(); i++) {
            int randomIndex = rand.nextInt(entrances.size());
            int[] randomEntrance = entrances.get(randomIndex);
            TrackerCat cat = new TrackerCat(configuration, intCoordinatesToDoubleCoords(randomEntrance));
            cats.add(cat);
        }

        for (int i = 0; i < configuration.trackerCats(); i++) {
            int randomIndex = rand.nextInt(entrances.size());
            int[] randomEntrance = entrances.get(randomIndex);
            var cat = new TunnelCamperCat(configuration, intCoordinatesToDoubleCoords(randomEntrance));
            cats.add(cat);
        }
    }

    private double[] intCoordinatesToDoubleCoords(int[] coords) {
        return new double[] {coords[0], coords[1]};
    }

    @Override
    public void updateCats(GameState gameState) {
        for (var cat:this.cats) {
            var m = cat.move(gameState);
            if (m.isPresent()) {
                var mouseSpawnPosition = gameState.getTunnelRespawnPosition();
                var position = mouseSpawnPosition.node().getPosition();
                var level = mouseSpawnPosition.level();
                m.get().setPosition(position[0] + 0.5, position[1] + 0.5);
                m.get().setCurrentLevel(level);
            }
        }
        gameState.setCats(cats.stream().map(c -> {
            Cat cr = new Cat();
            var pos = c.getPos();
            cr.setPos(pos);
            return cr;
        }).toList());
    }
}
