package at.ac.tuwien.foop.server.game.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a snapshot of cat positions at a specific point in time.
 */
@RequiredArgsConstructor
@Getter
public class CatSnapshot {

    /**
     * The positions of the cats at the time of the snapshot.
     */
    private final List<double[]> catPositions;

    /**
     * The timestamp when the snapshot was created.
     */
    private final LocalDateTime timeStamp = LocalDateTime.now();
}
