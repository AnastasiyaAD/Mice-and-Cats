package at.ac.tuwien.foop.server.game;


import java.time.Duration;

/**
 * @param tickRate            Server tick rate (updates per second)
 * @param mouseSpeed          Mouse speed per second in %, relative to the game field width (100% = moves through whole game field in the x-axis in one second)
 * @param catSpeed            Cat speed per second in %, relative to the game field width (100% = moves through whole game field in the x-axis in one second)
 * @param catSnapshotDuration Duration snapshot of previously known cat positions is shows inside tunnels
 */
public record Configuration(int tickRate, int mouseSpeed, int catSpeed, Duration catSnapshotDuration) {
}
