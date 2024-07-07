package at.ac.tuwien.foop.server.game.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CatSnapshot {

    private final List<double[]> catPositions;
    private final LocalDateTime timeStamp = LocalDateTime.now();
}
