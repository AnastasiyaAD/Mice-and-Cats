package at.ac.tuwien.foop.server.game.state;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

@Data
public class GameState {

    private final GameField gameField = new GameField();
    private final KeySetView<Mouse, Boolean> mice = ConcurrentHashMap.newKeySet();
    private LocalDateTime gameStart;


    public GameState() {
    }


}
