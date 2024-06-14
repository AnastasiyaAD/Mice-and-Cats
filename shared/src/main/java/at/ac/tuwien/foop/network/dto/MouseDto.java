package at.ac.tuwien.foop.network.dto;

import java.util.UUID;

public record MouseDto(double[] position, UUID clientId, String username) {
}
