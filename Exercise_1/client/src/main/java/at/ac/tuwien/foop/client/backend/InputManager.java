package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.network.dto.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class InputManager implements KeyListener {

  private final int LEFT = 37;
  private final int RIGHT = 39;
  private final int UP = 38;
  private final int DOWN = 40;
  private final int SPACE = 32;
  private Client client;
  /** Creates a new instance of InputManager */
  public InputManager(Client client) {
    this.client = client;
  }

  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case LEFT:
        client.addDirection(Direction.WEST);
        break;
      case RIGHT:
        client.addDirection(Direction.EAST);
        break;
      case UP:
        client.addDirection(Direction.NORTH);
        break;
      case DOWN:
        client.addDirection(Direction.SOUTH);
        break;
      case SPACE:
        client.sendLevelChange();
      default:
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case LEFT:
        client.removeDirection(Direction.WEST);
        break;
      case RIGHT:
        client.removeDirection(Direction.EAST);
        break;
      case UP:
        client.removeDirection(Direction.NORTH);
        break;
      case DOWN:
        client.removeDirection(Direction.SOUTH);
        break;
      default:
        break;
    }
  }
}
