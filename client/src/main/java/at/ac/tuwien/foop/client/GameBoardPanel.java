package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameBoardPanel extends JPanel {

  private int fieldPositionX;
  private int fieldPositionY;
  private final int width;
  private final int height;
  private static int scale = 50;
  private String clientId;
  // FIXME: Why is this static?
  private HashMap<String, Mouse> mice = new HashMap<>();
  private List<Cat> cats = new ArrayList<>();

  /**
   * Creates a new instance of GameBoardPanel
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

  public void updateBoard(GameStateDto gameState) {
    var mice = gameState.mice();
    // TODO unnecessary repetition of same calculation
    var fieldScaleX = (double) width / (gameState.gameField()[0] + 1);
    var fieldScaleY = (double) height / (gameState.gameField()[1] + 1);
    for (var mouse : mice) {
      String id = mouse.clientId().toString();
      var clientMouse = this.mice.get(id);
      // Add mouse if not already encountered
      if (clientMouse == null) {
        clientMouse = new Mouse();
        this.mice.put(id, clientMouse);
      }

      clientMouse.setXpoistion(
              (int) (mouse.position()[0] * fieldScaleX) + fieldPositionX
      );
      clientMouse.setYposition(
              (int) (mouse.position()[1] * fieldScaleY) + fieldPositionY
      );
      clientMouse.setDirection(1);
      clientMouse.setTunnel((int) mouse.level());
    }
    setCatPosition(gameState);
    this.repaint();
  }

  private void setCatPosition(GameStateDto gameState) {
    if (this.cats.isEmpty()) {
      this.cats =
        gameState
          .cats()
          .stream()
          .map(c ->
            new Cat(
              ((int) Math.round(c.position()[0] * scale + fieldPositionX)),
              ((int) Math.round(c.position()[1] * scale + fieldPositionY))
            )
          )
          .toList();
    } else {
      for (int i = 0; i < this.cats.size(); i++) {
        var cat = this.cats.get(i);
        var serverCatPosition = gameState.cats().get(i).position();
        cat.setXPosition(
          (int) Math.round(serverCatPosition[0] * scale + fieldPositionX)
        );
        cat.setYPosition(
          (int) Math.round(serverCatPosition[1] * scale + fieldPositionY)
        );
      }
    }
  }

  private void drawUnderground(Graphics2D g, int tunnel) {
    g.drawImage(
      new ImageIcon(
        MessageFormat.format("public/playing_field/tunnel_{0}.png", tunnel)
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
  }

  private void drawSurface(Graphics2D g) {
    g.drawImage(
      new ImageIcon("public/playing_field/grass.png").getImage(),
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

  public void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    Graphics2D g = (Graphics2D) gr;
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    g.drawString("Mice and Cats in a Network Game", 300, 25);
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
    repaint();
  }

  public void add(String clientName) {
    clientId = clientName;
  }
}
