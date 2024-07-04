package at.ac.tuwien.foop.network.dto;

import java.util.UUID;

/**
 *
 * @param position
 * @param clientId
 * @param username
 * @param level 0 = surface, 1 = tunnel 1 etc...
 */
public record MouseDto(double[] position, UUID clientId, String username, int level, int tunnelVote) {
}
