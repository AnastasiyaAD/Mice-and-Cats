package at.ac.tuwien.foop.server.game.state;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

@Data
public class GameState {

    private final GameField gameField = new GameField();
    private final Map<UUID, Mouse> mice = new ConcurrentHashMap<>();
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

    public Mouse getMouse(UUID clientId) {
        return this.mice.get(clientId);
    }

}
