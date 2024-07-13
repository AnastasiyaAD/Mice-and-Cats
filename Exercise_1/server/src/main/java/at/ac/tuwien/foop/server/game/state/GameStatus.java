package at.ac.tuwien.foop.server.game.state;

/**
 * Represents the various statuses that the game can be in.
 */
public enum GameStatus {
    /**
     * Initial state of the game before it has started.
     */
    INIT,

    /**
     * State of the game when it is currently running.
     */
    RUNNING,

    /**
     * State of the game when the time has run out.
     */
    TIME_OUT,

    /**
     * State of the game when the mice have won.
     */
    MICE_WON
}
