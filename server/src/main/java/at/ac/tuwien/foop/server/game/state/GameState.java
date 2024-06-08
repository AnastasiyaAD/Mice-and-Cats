package at.ac.tuwien.foop.server.game.state;

import at.ac.tuwien.foop.server.network.ClientManager;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameState {

    private final GameField gameField = new GameField();
    private final List<ClientManager> clientManagers = new ArrayList<>();
    private LocalDateTime gameStart;


    public GameState() {
    }
}
