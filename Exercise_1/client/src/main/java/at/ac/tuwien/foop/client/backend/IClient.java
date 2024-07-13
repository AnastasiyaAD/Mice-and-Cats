package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.network.dto.Direction;

/**
 * Interface for a client in the "Mice and Cats in a Network Game".
 * Defines methods for connecting, disconnecting, sending directions, and receiving messages.
 */
public interface IClient {

    /**
     * Connects to the server with the specified host and port.
     *
     * @param host The host of the server.
     * @param port The port of the server.
     * @throws Exception If an error occurs while connecting.
     */
    public void connect(String host, int port) throws Exception;

    /**
     * Disconnects from the server.
     *
     * @throws Exception If an error occurs while disconnecting.
     */
    public void disconnect() throws Exception;

    /**
     * Sends a direction action request to the server.
     *
     * @param direction The direction to be sent.
     */
    public void sendDirection(Direction direction);

    /**
     * Receives a JSON message from the server and processes it.
     *
     * @param json The JSON message received from the server.
     */
    public void receive(String json);
}
