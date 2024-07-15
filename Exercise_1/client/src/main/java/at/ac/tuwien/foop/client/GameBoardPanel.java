package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import lombok.Setter;

/**
 * The panel representing the game board for the "Mice and Cats in a Network Game".
 */
public class GameBoardPanel extends JPanel {

  private int fieldPositionX;
  private int fieldPositionY;
  private String state = "";
  private final int width;
  private final int height;
  private static int scale = 50;
  private String clientId;
  private HashMap<String, Mouse> mice = new HashMap<>();
  private List<Cat> cats = new ArrayList<>();
  private boolean catSeenInTunnel = false;

  /**
   * Creates a new instance of GameBoardPanel.
   *
   * @param x      The x position of the game field.
   * @param y      The y position of the game field.
   * @param width  The width of the game field.
   * @param height The height of the game field.
   */
  public GameBoardPanel(int x, int y, int width, int height) {
    this.fieldPositionX = x;
    this.fieldPositionY = y;
    this.width = width;
    this.height = height;
    setSize(width + x, height + y);
    setFocusable(true);
  }

  @Setter
  private String name;

  /**
   * Updates the game board with the current game state.
   *
   * @param gameState The current game state.
   */
  public void updateBoard(GameStateDto gameState) {
    state = gameState.status().name();
    var mice = gameState.mice();
    var fieldScaleX = (double) width / (gameState.gameField()[0] + 1);
    var fieldScaleY = (double) height / (gameState.gameField()[1] + 1);
    for (var mouse : mice) {
      String id = mouse.clientId().toString();
      var clientMouse = this.mice.get(id);
      if (clientMouse == null) {
        clientMouse = new Mouse();
        this.mice.put(id, clientMouse);
      }
      int middleMouse = clientMouse.getSize() / 2;

      clientMouse.setXpoistion(
              (int) (mouse.position()[0] * fieldScaleX) + fieldPositionX - middleMouse
      );
      clientMouse.setYposition(
              (int) (mouse.position()[1] * fieldScaleY) + fieldPositionY - middleMouse
      );
      clientMouse.setDirection(1);
      clientMouse.setTunnel((int) mouse.level());
    }
    setCatPosition(gameState);
    this.repaint();
  }

  /**
   * Sets the positions of the cats on the game board.
   *
   * @param gameState The current game state.
   */
  private void setCatPosition(GameStateDto gameState) {
    var myMouse = this.mice.get(this.clientId);
    var myTunnel = myMouse.getTunnel();
    var seenCats = gameState.catSnapshots().get(myTunnel);
    if (seenCats != null) {
      System.out.println(seenCats);
      this.catSeenInTunnel = true;
    } else {
      this.catSeenInTunnel = false;
    }
    if (this.cats.isEmpty()) {
      this.cats =
              gameState
                      .cats()
                      .stream()
                      .map(c ->
                              new Cat(
                                      ((int) Math.round(c.position()[0] * scale - fieldPositionX)),
                                      ((int) Math.round(c.position()[1] * scale - fieldPositionY))
                              )
                      )
                      .toList();
    } else {
      for (int i = 0; i < this.cats.size(); i++) {
        var cat = this.cats.get(i);
        int middleCat = cat.getSize() / 2;
        // Set position if snapshot
        double[] serverCatPosition;
        if (seenCats != null) {
          serverCatPosition = seenCats.catPositions().get(i);
        } else {
          serverCatPosition = gameState.cats().get(i).position();
        }
        cat.setXPosition(
                (int) Math.round(
                        serverCatPosition[0] * scale + fieldPositionX - middleCat
                )
        );
        cat.setYPosition(
                (int) Math.round(
                        serverCatPosition[1] * scale + fieldPositionY - middleCat
                )
        );
      }
    }
  }

  /**
   * Draws the underground portion of the game board for a given tunnel.
   *
   * @param g      The Graphics2D object for drawing.
   * @param tunnel The tunnel level to draw.
   */
  private void drawUnderground(Graphics2D g, int tunnel) {

    g.drawImage(
            new ImageIcon(
                    MessageFormat.format(
                            "Exercise_1/public/playing_field/tunnel_{0}.png",
                            tunnel
                    )
            )
                    .getImage(),
            fieldPositionX,
            fieldPositionY,
            null
    );
    for (var mouse : this.mice.values()) {
      if (mouse.getTunnel() == tunnel) {
        g.drawImage(
                mouse.getBuffImage(),
                mouse.getXposition(),
                mouse.getYposition(),
                this
        );
      }
    }
    if (this.catSeenInTunnel) {
      for (var cat : cats) {
        g.drawImage(
                cat.getImageBuff(),
                cat.getXPosition(),
                cat.getYPosition(),
                this
        );
      }
    }
  }

  /**
   * Draws the game over screen.
   *
   * @param g The Graphics2D object for drawing.
   */
  private void drawGameOver(Graphics2D g) {
    g.drawImage(
            new ImageIcon("Exercise_1/public/gameover.png").getImage(),
            fieldPositionX,
            fieldPositionY,
            null
    );
  }

  /**
   * Draws the game won screen.
   *
   * @param g The Graphics2D object for drawing.
   */
  private void drawGameWon(Graphics2D g) {
    g.drawImage(
            new ImageIcon("Exercise_1/public/victory.png").getImage(),
            fieldPositionX,
            fieldPositionY,
            null
    );
  }

  /**
   * Draws the surface portion of the game board.
   *
   * @param g The Graphics2D object for drawing.
   */
  private void drawSurface(Graphics2D g) {
    g.drawImage(
            new ImageIcon("Exercise_1/public/playing_field/grass.png").getImage(),
            fieldPositionX,
            fieldPositionY,
            null
    );
    for (var mouse : this.mice.values()) {
      if (mouse.getTunnel() == 0) {
        g.drawImage(
                mouse.getBuffImage(),
                mouse.getXposition(),
                mouse.getYposition(),
                this
        );
      }
    }
    for (var cat : cats) {
      g.drawImage(
              cat.getImageBuff(),
              cat.getXPosition(),
              cat.getYPosition(),
              this
      );
    }
  }

  @Override
  public void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    Graphics2D g = (Graphics2D) gr;
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    g.drawString("Mice and Cats in a Network Game", 300, 25);
    switch (this.state) {
      case "TIME_OUT":
        drawGameOver(g);
        break;
      case "RUNNING":
        var player = this.mice.get(clientId);
        if (player == null) {
          drawSurface(g);
        } else {
          int tunnel = player.getTunnel();
          if (tunnel == 0) {
            drawSurface(g);
          } else {
            drawUnderground(g, tunnel);
          }
        }
        break;
      case "MICE_WON":
        drawGameWon(g);
        break;
      default:
        drawSurface(g);
        break;
    }

    repaint();
  }

  /**
   * Sets the client ID for the game board panel.
   *
   * @param clientName The client ID to set.
   */
  public void add(String clientName) {
    clientId = clientName;
  }
}
