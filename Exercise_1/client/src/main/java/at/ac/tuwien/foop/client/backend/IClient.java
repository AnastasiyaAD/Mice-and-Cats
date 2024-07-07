package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.network.dto.Direction;

public interface IClient {
    /**
     *
     */
    public void connect(String host, int port) throws Exception;
    /**
     *
     */
    public void disconnect() throws Exception;

    /**
     * @param direction User input of direction
     */
    public void sendDirection(Direction direction);
    public void receive(String json);
}
