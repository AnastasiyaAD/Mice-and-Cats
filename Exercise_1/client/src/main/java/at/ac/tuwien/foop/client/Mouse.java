package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.Direction;
import lombok.Getter;
import lombok.Setter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Represents a mouse in the game, with properties for position, direction, and image handling.
 */
public class Mouse {

    private Image[] mouseImg;
    private BufferedImage ImageBuff;
    private String mouseID;
    private int posiX = -1, posiY = -1;
    /**
     * -- GETTER --
     * Gets the current direction of the mouse.
     *
     * @return The current direction of the mouse.
     */
    @Getter
    private int direction = 1;

    private static int startPositionX = 60;
    private static int startPositionY = 33;
    private static final int SIZE = 40;
    private Direction sDirection;
    private boolean isUnderground;
    @Getter
    @Setter
    private int tunnel;
    @Getter
    @Setter
    private Integer tunnelVote;

    /**
     * Gets the server direction of the mouse.
     *
     * @return The server direction of the mouse.
     */
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

    /**
     * Creates a new instance of Mouse with default start positions.
     */
    public Mouse() {
        posiX = startPositionX;
        posiY = startPositionY;
        loadImage(0);
    }

    /**
     * Creates a new instance of Mouse with specified parameters.
     *
     * @param id          The ID of the mouse.
     * @param x           The x-coordinate of the mouse.
     * @param y           The y-coordinate of the mouse.
     * @param dir         The direction of the mouse.
     * @param tunnelS     The tunnel number.
     * @param tunnelVoteS The tunnel vote.
     */
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

    /**
     * Loads the image for the mouse.
     *
     * @param a The index offset for the image based on direction the mouse is facing.
     */
    public void loadImage(int a) {
        mouseImg = new Image[4];
        for (int i = a; i < mouseImg.length + a; i++) {
            mouseImg[i - a] =
                    new ImageIcon("Exercise_1/public/rat_player/" + i + ".png").getImage();
        }
        ImageBuff =
                new BufferedImage(
                        mouseImg[direction - 1].getWidth(null),
                        mouseImg[direction - 1].getHeight(null),
                        BufferedImage.TYPE_INT_RGB
                );
        ImageBuff.createGraphics().drawImage(mouseImg[direction - 1], 0, 0, null);
    }

    /**
     * Gets the buffered image of the mouse.
     *
     * @return The buffered image of the mouse.
     */
    public BufferedImage getBuffImage() {
        return ImageBuff;
    }

    /**
     * Gets the x-coordinate position of the mouse.
     *
     * @return The x-coordinate position of the mouse.
     */
    public int getXposition() {
        return posiX;
    }

    /**
     * Gets the y-coordinate position of the mouse.
     *
     * @return The y-coordinate position of the mouse.
     */
    public int getYposition() {
        return posiY;
    }

    /**
     * Gets whether the mouse is underground.
     *
     * @return true if the mouse is underground, false otherwise.
     */
    public boolean getIsUnderground() {
        return isUnderground;
    }

    /**
     * Gets the size of the mouse.
     *
     * @return The size of the mouse.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Sets the x-coordinate position of the mouse.
     *
     * @param x The x-coordinate to set.
     */
    public void setXpoistion(int x) {
        posiX = x;
    }

    /**
     * Sets the y-coordinate position of the mouse.
     *
     * @param y The y-coordinate to set.
     */
    public void setYposition(int y) {
        posiY = y;
    }

    /**
     * Sets the direction of the mouse and updates its image.
     *
     * @param dir The direction to set.
     */
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
