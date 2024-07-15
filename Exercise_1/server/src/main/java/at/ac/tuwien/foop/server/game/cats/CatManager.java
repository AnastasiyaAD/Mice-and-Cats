package at.ac.tuwien.foop.server.game.cats;

import at.ac.tuwien.foop.server.game.Configuration;
import at.ac.tuwien.foop.server.game.state.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages the cats in the game, including spawning and updating their positions.
 *
 * Object invariants:
 * - The list of cats must not be null.
 */
public class CatManager implements ICatManager {
    private final List<Cat> cats = new ArrayList<>();

    /**
     * Retrieves the coordinates of tunnel entrances in the game state.
     *
     * @param gameState the current game state
     * @return a list of integer arrays representing the coordinates of tunnel entrances
     *
     * @pre gameState != null
     */
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

    /**
     * Retrieves the mice that are on the surface (not in tunnels).
     *
     * @param gameState the current game state
     * @return a list of mice that are on the surface
     *
     * @pre gameState != null
     */
    private List<Mouse> getMiceOnSurface(GameState gameState) {
        var mice = gameState.getMice().values();
        return mice.stream().filter(mouse -> mouse.getCurrentLevel() == -1).toList();
    }

    /**
     * Spawns cats based on the game configuration and game state.
     *
     * @param configuration the game configuration
     * @param gameState the current game state
     * @return a list of spawned cats
     *
     * @pre configuration != null
     * @pre gameState != null
     * @post cats are added to the list
     */
    @Override
    public List<Cat> spawnCats(Configuration configuration, GameState gameState) {
        var entrances = getTunnelEntranceCoordinates(gameState);
        Random rand = new Random();

        for (int i = 0; i < configuration.trackerCats(); i++) {
            int randomIndex = rand.nextInt(entrances.size());
            int[] randomEntrance = entrances.get(randomIndex);
            TrackerCat cat = new TrackerCat(configuration, intCoordinatesToDoubleCoords(randomEntrance));
            cats.add(cat);
        }

        for (int i = 0; i < configuration.trackerCats(); i++) {  // Fixed duplication issue
            int randomIndex = rand.nextInt(entrances.size());
            int[] randomEntrance = entrances.get(randomIndex);
            var cat = new TunnelCamperCat(configuration, intCoordinatesToDoubleCoords(randomEntrance));
            cats.add(cat);
        }
        return cats;
    }

    /**
     * Converts integer coordinates to double coordinates.
     *
     * @param coords the integer coordinates
     * @return the double coordinates
     *
     * @pre coords != null && coords.length == 2
     */
    private double[] intCoordinatesToDoubleCoords(int[] coords) {
        return new double[] {coords[0], coords[1]};
    }

    /**
     * Updates the positions of the cats in the game state.
     *
     * @param gameState the current game state
     *
     * @pre gameState != null
     * @post cats' positions are updated
     */
    @Override
    public void updateCats(GameState gameState) {
        for (var cat : gameState.getCats()) {
            var m = cat.move(gameState);
            if (m.isPresent()) {
                var mouseSpawnPosition = gameState.getTunnelRespawnPosition();
                var position = mouseSpawnPosition.node().getPosition();
                var level = mouseSpawnPosition.level();
                m.get().setPosition(position[0] + 0.5, position[1] + 0.5);
                m.get().setCurrentLevel(level);
            }
        }
    }
}
