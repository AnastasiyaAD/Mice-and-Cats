package at.ac.tuwien.foop.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {

  /** Creates a new instance of GameBoardPanel */
  private Mouse mouse;
  private int width = 810;
  private int height = 861;
  private static ArrayList<Mouse> mice;
  private boolean gameStatus;

  public GameBoardPanel(Mouse mouse, boolean gameStatus) {
    this.mouse = mouse;
    this.gameStatus = gameStatus;
    setSize(width, height);
    setBounds(30, 0, width + 20, height);
    addKeyListener(new InputManager(mouse));
    setFocusable(true);

    mice = new ArrayList<Mouse>(100);

    for (int i = 0; i < 100; i++) {
      mice.add(null);
    }
  }

  public void paintComponent(Graphics gr) {
    super.paintComponent(gr);
    Graphics2D g = (Graphics2D) gr;

    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());

    g.drawImage(
      new ImageIcon("public/playing_field/Background_without.png").getImage(),
      60,
      45,
      null
    );
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
    g.drawString("Mice and Cats in a Network Game", 255, 30);
    if (gameStatus) {
      g.drawImage(
        mouse.getBuffImage(),
        mouse.getXposition(),
        mouse.getYposition(),
        this
      );

      for (int i = 1; i < mice.size(); i++) {
        if (mice.get(i) != null) g.drawImage(
          mice.get(i).getBuffImage(),
          mice.get(i).getXposition(),
          mice.get(i).getYposition(),
          this
        );
      }
    }

    repaint();
  }

  public void registerNewMouse(Mouse newMouse) {
    mice.set(newMouse.getMouseID(), newMouse);
  }

  public void removeMouse(int mouseID) {
    mice.set(mouseID, null);
  }

  public Mouse getMouse(int id) {
    return mice.get(id);
  }

  public void setGameStatus(boolean status) {
    gameStatus = status;
  }

  public static ArrayList<Mouse> getClients() {
    return mice;
  }
}
