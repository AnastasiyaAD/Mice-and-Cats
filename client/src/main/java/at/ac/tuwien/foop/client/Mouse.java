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

  public void checkWall(int xP, int yP) {
    isWall = false;
    switch (direction) {
      case 1:
        isWall = yP - velocityY < startPositionY;
        break;
      case 2:
        isWall = xP - velocityX < startPositionX;
        break;
      case 3:
        isWall = yP + velocityY > startPositionY + height - sizeMouse;
        break;
      case 4:
        isWall = xP + velocityX > startPositionX + width - sizeMouse;
        break;
    }
    System.out.println("isWall = " + isWall);
  }
}
