package at.ac.tuwien.foop.server.game.state;

import lombok.Data;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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

    public Mouse getMouse(UUID clientId) {
        return this.mice.get(clientId);
    }

}
