package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.CatDto;
import at.ac.tuwien.foop.network.dto.GameStateDto;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import lombok.Setter;

public class GameBoardPanel extends JPanel {

  private int fieldPositionX;
  private int fieldPositionY;

  /**
   * Creates a new instance of GameBoardPanel
   */
  public GameBoardPanel(int x, int y, int width, int height) {
    this.fieldPositionX = x;
    this.fieldPositionY = y;
    setSize(width + x, height + y);
    setFocusable(true);
    mice = new HashMap<>();
  }

  // FIXME: Why is this static?
  private HashMap<String, Mouse> mice;
  private List<Cat> cats = new ArrayList<>();

  @Setter
  private String username;

  public void updateBoard(GameStateDto gameState) {
    var mice = gameState.mice();
    for (var mouse : mice) {
      // FIXME: Do we use UUID here or username to identify the mice?
      String username = mouse.username();
      var clientMouse = this.mice.get(username);
      // Add mouse if not already encountered
      if (clientMouse == null) {
        clientMouse = new Mouse();
        this.mice.put(username, clientMouse);
      }

      clientMouse.setXpoistion((int) mouse.position()[0]);
      clientMouse.setYposition((int) mouse.position()[1]);
      clientMouse.setDirection(1);
    }
    setCatPosition(gameState);
    this.repaint();
  }

  private void setCatPosition(GameStateDto gameState) {
    if (this.cats.isEmpty()) {
      this.cats = gameState.cats().stream().map(c -> new Cat(((int) c.position()[0]) * 50,
              ((int) c.position()[0]) * 50)).toList();
    } else {
      for (int i = 0; i < this.cats.size(); i++) {
        var cat = this.cats.get(i);
        var serverCatPosition = gameState.cats().get(i).position();
        cat.setXPosition(((int) serverCatPosition[0]) * 50);
        cat.setYPosition(((int) serverCatPosition[1]) * 50);
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
    repaint();
  }

  public void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    Graphics2D g = (Graphics2D) gr;
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());

    g.drawImage(
      new ImageIcon("public/playing_field/grass.png").getImage(),
      fieldPositionX,
      fieldPositionY,
      null
    );
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    g.drawString("Mice and Cats in a Network Game", 300, 25);

    // FIXME: Draw underground if OUR mouse is in the underground
    // drawUnderground(g, tunnel)
    for (var mouse : this.mice.values()) {
      g.drawImage(
        mouse.getBuffImage(),
        mouse.getXposition(),
        mouse.getYposition(),
        this
      );
    }
    for (var cat : cats) {
      g.drawImage(
              cat.getImageBuff(),
              cat.getXPosition(),
              cat.getYPosition(),
              this
      );
    }

    repaint();
  }
}
