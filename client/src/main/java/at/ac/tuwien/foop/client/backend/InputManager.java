package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.client.Mouse;
import at.ac.tuwien.foop.network.dto.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

  private final int LEFT = 37;
  private final int RIGHT = 39;
  private final int UP = 38;
  private final int DOWN = 40;
  private final int SPACE = 32;
  private Client client;
  private Mouse mouse;

  /** Creates a new instance of InputManager */
  public InputManager(Client client) {
    this.client = client;
  }

  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case LEFT:
        client.sendDirection(Direction.WEST);
        break;
      case RIGHT:
        client.sendDirection(Direction.EAST);
        break;
      case UP:
        client.sendDirection(Direction.NORTH);
        break;
      case DOWN:
        client.sendDirection(Direction.SOUTH);
        break;
      case SPACE:
        //TODO: client sendDirection Tunnel
      default:
        break;
    }
  }

  public void keyReleased(KeyEvent e) {}
}
