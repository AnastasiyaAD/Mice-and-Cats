package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.client.Mouse;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

  private final int LEFT = 37;
  private final int RIGHT = 39;
  private final int UP = 38;
  private final int DOWN = 40;
  private Client client;
  private Mouse mouse;

  /** Creates a new instance of InputManager */
  public InputManager(Mouse mouse) {
    this.client = Client.getGameClient();
    this.mouse = mouse;
  }

  public void keyTyped(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    int direction = mouse.getDirection();
    switch (e.getKeyCode()) {
      case LEFT:
        mouse.moveLeft();

        if (!mouse.getIsWall()) {
          client.sendDirection(mouse.getServerDirection());
        }

        break;
      case RIGHT:
        mouse.moveRight();

        if (!mouse.getIsWall()) {
          client.sendDirection(mouse.getServerDirection());
        }

        break;
      case UP:
        mouse.moveForward();

        if (!mouse.getIsWall()) {
          client.sendDirection(mouse.getServerDirection());
        }

        break;
      case DOWN:
        mouse.moveBackward();

        if (!mouse.getIsWall()) {
          client.sendDirection(mouse.getServerDirection());
        }

        break;
      default:
        break;
    }
  }

  public void keyReleased(KeyEvent e) {}
}
