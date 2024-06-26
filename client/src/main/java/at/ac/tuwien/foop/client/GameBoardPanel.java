package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.client.backend.InputManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {

  /** Creates a new instance of GameBoardPanel */
  private Mouse mouse;
  private int width = 800;
  private int height = 850;
  private static ArrayList<Mouse> mice;
  private boolean gameStatus;
  int z = 0;
  int y = 1;
  int k = 0;
  int l = 1;

  public GameBoardPanel(Mouse mouse, boolean gameStatus) {
    this.mouse = mouse;
    this.gameStatus = gameStatus;
    setSize(width, height);
    setBounds(0, 0, width + 30, height + 20);
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
      new ImageIcon("public/playing_field/grass.png").getImage(),
      60,
      33,
      null
    );
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
    g.drawString("Mice and Cats in a Network Game", 300, 25);

    if (gameStatus) {
      if (mouse.getIsUnderground()) {
        g.drawImage(
          new ImageIcon(
            "public/playing_field/tunnel_" + mouse.getTunnel() + ".png"
          )
            .getImage(),
          60,
          33,
          null
        );
        for (int i = 0; i < mice.size(); i++) {
          if (mice.get(i) != null) {
            if (mouse.getMouseID() != mice.get(i).getMouseID()) {
              g.drawImage(
                null,
                mice.get(i).getXposition(),
                mice.get(i).getYposition(),
                this
              );
            }
          }
        }
      } else {
        for (int i = 0; i < mice.size(); i++) {
          if (mice.get(i) != null) {
            if (mouse.getMouseID() != mice.get(i).getMouseID()) {
              g.drawImage(
                mice.get(i).getBuffImage(),
                mice.get(i).getXposition(),
                mice.get(i).getYposition(),
                this
              );
            }
          }
        }
      }
      g.drawImage(
        mouse.getBuffImage(),
        mouse.getXposition(),
        mouse.getYposition(),
        this
      );
      z = mouse.getXposition();
      k = mouse.getYposition();
      if (z != y || k != l) {
        System.out.printf(
          "x={%s}, y={%s}\n",
          mouse.getXposition(),
          mouse.getYposition()
        );
      }
      y = mouse.getXposition();
      l = mouse.getYposition();
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
