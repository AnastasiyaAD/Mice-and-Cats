package at.ac.tuwien.foop.server.game.state;

import lombok.Data;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Data
public class GameState {

    private final GameField gameField = new GameField();
    private final Map<UUID, Mouse> mice = new ConcurrentHashMap<>();
    private final Map<Integer, CatSnapshot> catSnapshots = new ConcurrentHashMap<>();
    @Setter
    private List<Cat> cats = new ArrayList<>();
    private LocalDateTime gameStart;
    private Duration gameDuration = Duration.of(3, ChronoUnit.MINUTES);
    private GameStatus gameStatus = GameStatus.INIT;

    public void addMouse(UUID clientId, Mouse mouse) {
        if (this.mice.containsKey(clientId)) {
            System.out.println("Mouse exists, cannot add");
            throw new RuntimeException("Mouse already exists!");
        }
        var pos = getTunnelRespawnPosition();
        var coordinates = pos.node().getPosition();

        mouse.setPosition(coordinates[0] + 0.5, coordinates[1] + 0.5);
        mouse.setCurrentLevel(pos.level());
        this.mice.put(clientId, mouse);
    }

    public void addSnapshot(int level, List<Cat> cats) {
        var snapshot = new CatSnapshot(cats.stream().map(Cat::getPos).toList());
        catSnapshots.put(level, snapshot);
    }

    public void clearExpiredSnapshots(Duration expirationTime) {
        var expired = catSnapshots.entrySet().stream().filter(snapshot -> Duration.between(snapshot.getValue().getTimeStamp(), LocalDateTime.now()).minus(expirationTime).isNegative()).toList();
        expired.forEach(expiredSnapshot -> catSnapshots.remove(expiredSnapshot.getKey()));
    }

    public TunnelPosition getTunnelRespawnPosition() {
        Set<Integer> tunnels = gameField.getTunnels().keySet();

        // 1. Count the number of mice in each tunnel
        Map<Integer, Long> tunnelCounts = this.mice.values()
                .stream()
                .map(Mouse::getCurrentLevel)
                .filter(level -> level != 0)
                .collect(Collectors.groupingBy(level -> level, Collectors.counting()));

        // 2. If no mice are present in any tunnel, select any tunnel randomly
        if (tunnelCounts.isEmpty()) {
            Random random = new Random();
            var tunnel = random.nextInt(tunnels.size()) + 1;
            var tunnelNode = gameField.getTunnels().get(tunnel).getRandomNonDoorNode();
            return new TunnelPosition(tunnel, tunnelNode);
        }

        // 3. Find the minimum count of mice in any tunnel
        long minCount = tunnelCounts.values().stream().min(Long::compare).orElse(0L);

        // 4. Find tunnels with the minimum count of mice
        List<Integer> minTunnels = tunnelCounts.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == minCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Include tunnels with no mice if there are any
        minTunnels.addAll(tunnels.stream()
                .filter(tunnel -> !tunnelCounts.containsKey(tunnel))
                .toList());

        // 5. Choose randomly among the tunnels with the minimum count
        Random random = new Random();
        int selectedTunnel = minTunnels.get(random.nextInt(minTunnels.size()));

        // 6. Return the respawn position for the selected tunnel
        var tunnelNode = gameField.getTunnels().get(selectedTunnel).getRandomNonDoorNode();

        return new TunnelPosition(selectedTunnel, tunnelNode);
    }

    public Mouse getMouse(UUID clientId) {
        return this.mice.get(clientId);
    }

}
