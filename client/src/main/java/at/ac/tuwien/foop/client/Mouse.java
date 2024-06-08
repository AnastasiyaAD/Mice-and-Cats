package at.ac.tuwien.foop.client;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Mouse {

  private Image[] mouseImg;
  private BufferedImage ImageBuff;
  private int mouseID;
  private int posiX = -1, posiY = -1;
  private int direction = 1;
  private float velocityX = 0.03125f, velocityY = 0.03125f;
  private int width = 810, height = 812;

  public int getDirection() {
    return direction;
  }

  /** Creates a new instance of Mouse */
  public Mouse() {
    while (posiX < 70 | posiY < 54 | posiY > height - 54 | posiX > width - 54) {
      posiX = (int) (Math.random() * width);
      posiY = (int) (Math.random() * height);
    }
    loadImage(4);
  }

  public Mouse(int x, int y, int dir, int id) {
    posiX = x;
    posiY = y;
    mouseID = id;
    direction = dir;
    loadImage(0);
  }

  public void loadImage(int a) {
    mouseImg = new Image[4];
    for (int i = a; i < mouseImg.length + a; i++) {
      mouseImg[i - a] = new ImageIcon("public/rat_player/" + i + ".png").getImage();
    }
    ImageBuff =
      new BufferedImage(
        mouseImg[direction - 1].getWidth(null),
        mouseImg[direction - 1].getHeight(null),
        BufferedImage.TYPE_INT_RGB
      );
    ImageBuff.createGraphics().drawImage(mouseImg[direction - 1], 0, 0, null);
  }

  public BufferedImage getBuffImage() {
    return ImageBuff;
  }

  public int getXposition() {
    return posiX;
  }

  public int getYposition() {
    return posiY;
  }

  public void setXpoistion(int x) {
    posiX = x;
  }

  public void setYposition(int y) {
    posiY = y;
  }

  public void moveLeft() {
    if (direction == 1 | direction == 3) {
      ImageBuff =
        new BufferedImage(
          mouseImg[3].getWidth(null),
          mouseImg[3].getHeight(null),
          BufferedImage.TYPE_INT_RGB
        );
      ImageBuff.createGraphics().drawImage(mouseImg[3], 0, 0, null);
      direction = 4;
    } else {
      int temp;

      temp = (int) (posiX - velocityX * posiX);
      if (checkCollision(temp, posiY) == false && temp < 70) {
        posiX = 70;
      } else if (checkCollision(temp, posiY) == false) {
        posiX = temp;
      }
    }
  }

  public void moveRight() {
    if (direction == 1 | direction == 3) {
      ImageBuff =
        new BufferedImage(
          mouseImg[1].getWidth(null),
          mouseImg[1].getHeight(null),
          BufferedImage.TYPE_INT_RGB
        );
      ImageBuff.createGraphics().drawImage(mouseImg[1], 0, 0, null);
      direction = 2;
    } else {
      int temp;
      temp = (int) (posiX + velocityX * posiX);
      if (checkCollision(temp, posiY) == false && temp > width - 54 + 20) {
        posiX = width - 54 + 20;
      } else if (checkCollision(temp, posiY) == false) {
        posiX = temp;
      }
    }
  }

  public void moveForward() {
    if (direction == 2 | direction == 4) {
      ImageBuff =
        new BufferedImage(
          mouseImg[0].getWidth(null),
          mouseImg[0].getHeight(null),
          BufferedImage.TYPE_INT_RGB
        );
      ImageBuff.createGraphics().drawImage(mouseImg[0], 0, 0, null);
      direction = 1;
    } else {
      int temp;
      temp = (int) (posiY - velocityY * posiY);
      if (checkCollision(posiX, temp) == false && temp < 54) {
        posiY = 54;
      } else if (checkCollision(posiX, temp) == false) {
        posiY = temp;
      }
    }
  }

  public void moveBackward() {
    if (direction == 2 | direction == 4) {
      ImageBuff =
        new BufferedImage(
          mouseImg[2].getWidth(null),
          mouseImg[2].getHeight(null),
          BufferedImage.TYPE_INT_RGB
        );
      ImageBuff.createGraphics().drawImage(mouseImg[2], 0, 0, null);
      direction = 3;
    } else {
      int temp;
      temp = (int) (posiY + velocityY * posiY);
      if (checkCollision(posiX, temp) == false && temp > height - 54 + 54) {
        posiY = height - 54 + 54;
      } else if (checkCollision(posiX, temp) == false) {
        posiY = temp;
      }
    }
  }

  public void setMouseID(int id) {
    mouseID = id;
  }

  public int getMouseID() {
    return mouseID;
  }

  public void setDirection(int dir) {
    ImageBuff =
      new BufferedImage(
        mouseImg[dir - 1].getWidth(null),
        mouseImg[dir - 1].getHeight(null),
        BufferedImage.TYPE_INT_RGB
      );
    ImageBuff.createGraphics().drawImage(mouseImg[dir - 1], 0, 0, null);
    direction = dir;
  }

  public boolean checkCollision(int xP, int yP) {
    ArrayList<Mouse> clientMice = GameBoardPanel.getClients();
    int x, y;
    for (int i = 1; i < clientMice.size(); i++) {
      if (clientMice.get(i) != null) {
        x = clientMice.get(i).getXposition();
        y = clientMice.get(i).getYposition();
        if (direction == 1) {
          if (
            ((yP <= y + 54) && yP >= y) &&
            ((xP <= x + 54 && xP >= x) || (xP + 54 >= x && xP + 54 <= x + 54))
          ) {
            return true;
          }
        } else if (direction == 2) {
          if (
            ((xP + 54 >= x) && xP + 54 <= x + 54) &&
            ((yP <= y + 54 & yP >= y) || (yP + 54 >= y && yP + 54 <= y + 54))
          ) {
            return true;
          }
        } else if (direction == 3) {
          if (
            ((yP + 54 >= y) && yP + 54 <= y + 54) &&
            ((xP <= x + 54 && xP >= x) || (xP + 54 >= x && xP + 54 <= x + 54))
          ) {
            return true;
          }
        } else if (direction == 4) {
          if (
            ((xP <= x + 54) && xP >= x) &&
            ((yP <= y + 54 && yP >= y) || (yP + 54 >= y && yP + 54 <= y + 54))
          ) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
