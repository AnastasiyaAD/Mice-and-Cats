package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.client.Mouse;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

  private final int LEFT = 37;
  private final int RIGHT = 39;
  private final int UP = 38;
  private final int DOWN = 40;
  private static Client client;
  private Mouse mouse;

  /** Creates a new instance of InputManager */
  public InputManager(Mouse mouse) {
    this.mouse = mouse;
  }

  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    int direction = mouse.getDirection();
    switch (e.getKeyCode()) {
      case LEFT:
        if (direction == 1 | direction == 3 | direction == 2) {
          mouse.moveLeft();
          client.sendDirection(mouse.getServerDirection());
        } else {
          //TODO: get new coordinates from the server
        }
        break;
      case RIGHT:
        if (direction == 1 | direction == 3 | direction == 4) {
          mouse.moveRight();
          client.sendDirection(mouse.getServerDirection());
        } else {
          client.sendDirection(mouse.getServerDirection());
          //TODO: get new coordinates from the server
        }
        break;
      case UP:
        if (direction == 2 | direction == 4 | direction == 3) {
          mouse.moveForward();
          client.sendDirection(mouse.getServerDirection());
        } else {
          //TODO: get new coordinates from the server
        }
        break;
      case DOWN:
        if (direction == 2 | direction == 4 | direction == 1) {
          mouse.moveBackward();
          client.sendDirection(mouse.getServerDirection());
        } else {
          //TODO: get new coordinates from the server
        }
        break;
      default:
        break;
    }
  }

  public void keyReleased(KeyEvent e) {}
}
