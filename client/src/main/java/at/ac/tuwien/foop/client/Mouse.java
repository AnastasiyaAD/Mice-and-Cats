package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.Direction;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Mouse {

  private Image[] mouseImg;
  private BufferedImage ImageBuff;
  private String mouseID;
  private int posiX = -1, posiY = -1;
  private int direction = 1;

  private static int startPositionX = 60;
  private static int startPositionY = 33;
  private static final int SIZE = 40;
  private Direction sDirection;
  private boolean isUnderground;
  private int tunnel;
  private Integer tunnelVote;

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

  public Mouse(
    String id,
    int x,
    int y,
    int dir,
    int tunnelS,
    Integer tunnelVoteS
  ) {
    posiX = x;
    posiY = y;
    mouseID = id;
    direction = dir;
    tunnel = tunnelS;
    tunnelVote = tunnelVoteS;
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

  public int getSize() {
    return SIZE;
  }

  public Integer getTunnelVote() {
    return tunnelVote;
  }

  public void setTunnel(int number) {
    tunnel = number;
  }

  public void setTunnelVote(Integer number) {
    tunnelVote = number;
  }

  public void setXpoistion(int x) {
    posiX = x;
  }

  public void setYposition(int y) {
    posiY = y;
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
}
