package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.Direction;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Mouse {

  private Image[] mouseImg;
  private BufferedImage ImageBuff;
  private int mouseID;
  private int posiX = -1, posiY = -1;
  private int direction = 1;
  private int velocityX = 5, velocityY = 5;
  private int width = 750;
  private int height = 800;
  private int sizeMouse = 40;
  private static int startPositionX = 60;
  private static int startPositionY = 33;
  private Direction sDirection;
  private boolean isWall;
  private boolean isUnderground;
  private boolean isEntrance;
  private int tunnel;

  public int getDirection() {
    return direction;
  }

  public Direction getServerDirection() {
    sDirection =
      switch (direction) {
        case 1 -> Direction.NORTH;
        case 2 -> Direction.WEST;
        case 3 -> Direction.SOUTH;
        case 4 -> Direction.EAST;
        default -> Direction.NORTH;
      };
    return sDirection;
  }

  /** Creates a new instance of Mouse */
  public Mouse() {
    posiX = startPositionX;
    posiY = startPositionY;
    loadImage(0);
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
      mouseImg[i - a] =
        new ImageIcon("public/rat_player/" + i + ".png").getImage();
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

  public boolean getIsUnderground() {
    return isUnderground;
  }

  public int getTunnel() {
    return tunnel;
  }

  public boolean getIsEntrance() {
    return isEntrance;
  }

  public void setXpoistion(int x) {
    posiX = x;
  }

  public void setYposition(int y) {
    posiY = y;
  }

  public boolean getIsWall() {
    return isWall;
  }

  public void moveLeft() {
    int temp;
    temp = posiX - velocityX;
    checkWall(posiX, posiY);
    if (direction == 2 && !isWall) {
      posiX = temp;
    }
    ImageBuff =
      new BufferedImage(
        mouseImg[3].getWidth(null),
        mouseImg[3].getHeight(null),
        BufferedImage.TYPE_INT_RGB
      );
    ImageBuff.createGraphics().drawImage(mouseImg[3], 0, 0, null);
    direction = 2;
    System.out.println(
      "!!!!!moveLeft positionX = " +
      posiX +
      " positionY = " +
      posiY +
      " isWall = " +
      isWall
    );
  }

  public void moveRight() {
    int temp;
    temp = posiX + velocityX;
    checkWall(posiX, posiY);
    if (direction == 4 && !isWall) {
      posiX = temp;
    }
    ImageBuff =
      new BufferedImage(
        mouseImg[1].getWidth(null),
        mouseImg[1].getHeight(null),
        BufferedImage.TYPE_INT_RGB
      );
    ImageBuff.createGraphics().drawImage(mouseImg[1], 0, 0, null);
    direction = 4;
    System.out.println(
      "!!!!!moveRight positionX = " +
      posiX +
      " positionY = " +
      posiY +
      " isWall = " +
      isWall
    );
  }

  public void moveForward() {
    int temp;
    temp = posiY - velocityY;
    checkWall(posiX, posiY);
    if (direction == 1 && !isWall) {
      posiY = temp;
    }
    ImageBuff =
      new BufferedImage(
        mouseImg[0].getWidth(null),
        mouseImg[0].getHeight(null),
        BufferedImage.TYPE_INT_RGB
      );
    ImageBuff.createGraphics().drawImage(mouseImg[0], 0, 0, null);
    direction = 1;
    System.out.println(
      "!!!!!moveForward positionX = " +
      posiX +
      " positionY = " +
      posiY +
      " isWall = " +
      isWall
    );
  }

  public void moveBackward() {
    int temp;
    temp = posiY + velocityY;
    checkWall(posiX, posiY);
    if (direction == 3 && !isWall) {
      posiY = temp;
    }
    ImageBuff =
      new BufferedImage(
        mouseImg[2].getWidth(null),
        mouseImg[2].getHeight(null),
        BufferedImage.TYPE_INT_RGB
      );
    ImageBuff.createGraphics().drawImage(mouseImg[2], 0, 0, null);
    direction = 3;
    System.out.println(
      "!!!!!moveForward positionX = " +
      posiX +
      " positionY = " +
      posiY +
      " isWall = " +
      isWall
    );
  }

  public void exit() {
    boolean before = isUnderground;
    checkTunnelEntrance(posiX, posiY);
    if (isEntrance) {
      if (before) {
        isEntrance = false;
        isUnderground = false;
        tunnel = 0;
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

  public void checkWall(int x, int y) {
    isWall = false;
    switch (direction) {
      case 1:
        isWall = y - velocityY < startPositionY;
        break;
      case 2:
        isWall = x - velocityX < startPositionX;
        break;
      case 3:
        isWall = y + velocityY > startPositionY + height - sizeMouse;
        break;
      case 4:
        isWall = x + velocityX > startPositionX + width - sizeMouse;
        break;
    }
    System.out.println("isWall = " + isWall);
  }

  public void checkTunnelEntrance(int x, int y) {
    isUnderground = false;
    tunnel = 0;
    isEntrance = false;
    //tunnel 1 [2;14]
    if (
      x >= 50 + startPositionX &&
      x <= 100 + startPositionX &&
      y >= 650 + startPositionY &&
      y <= 700 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 1;
      isEntrance = true;
    }
    //tunnel 1 [3;11]
    if (
      x >= 100 + startPositionX &&
      x <= 150 + startPositionX &&
      y >= 500 + startPositionY &&
      y <= 550 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 1;
      isEntrance = true;
    }
    //tunnel 1 [6;12]
    if (
      x >= 250 + startPositionX &&
      x <= 300 + startPositionX &&
      y >= 550 + startPositionY &&
      y <= 600 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 1;
      isEntrance = true;
    }

    //tunnel 2 [6;12]
    if (
      x >= 250 + startPositionX &&
      x <= 300 + startPositionX &&
      y >= 300 + startPositionY &&
      y <= 350 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 2;
      isEntrance = true;
    }

    //tunnel 2 [9;10]
    if (
      x >= 400 + startPositionX &&
      x <= 450 + startPositionX &&
      y >= 450 + startPositionY &&
      y <= 500 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 2;
      isEntrance = true;
    }

    //tunnel 2 [11;8]
    if (
      x >= 500 + startPositionX &&
      x <= 550 + startPositionX &&
      y >= 350 + startPositionY &&
      y <= 400 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 2;
      isEntrance = true;
    }

    //tunnel 3 [7;3]
    if (
      x >= 300 + startPositionX &&
      x <= 350 + startPositionX &&
      y >= 100 + startPositionY &&
      y <= 150 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 3;
      isEntrance = true;
    }

    //tunnel 3 [9;6]
    if (
      x >= 400 + startPositionX &&
      x <= 450 + startPositionX &&
      y >= 200 + startPositionY &&
      y <= 250 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 3;
      isEntrance = true;
    }

    //tunnel 4 [11;2]
    if (
      x >= 500 + startPositionX &&
      x <= 550 + startPositionX &&
      y >= 50 + startPositionY &&
      y <= 100 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 4;
      isEntrance = true;
    }

    //tunnel 4 [14;4]
    if (
      x >= 650 + startPositionX &&
      x <= 700 + startPositionX &&
      y >= 150 + startPositionY &&
      y <= 200 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 4;
      isEntrance = true;
    }

    //tunnel 5 [8;16]
    if (
      x >= 350 + startPositionX &&
      x <= 400 + startPositionX &&
      y >= 750 + startPositionY &&
      y <= 800 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 5;
      isEntrance = true;
    }

    //tunnel 5 [13;14]
    if (
      x >= 600 + startPositionX &&
      x <= 650 + startPositionX &&
      y >= 650 + startPositionY &&
      y <= 700 + startPositionY
    ) {
      isUnderground = true;
      tunnel = 5;
      isEntrance = true;
    }
  }

  public void checkTunnelBorders(int x, int y) {
    //TODO: checkTunnelBorders
  }
}
