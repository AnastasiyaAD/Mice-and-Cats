package at.ac.tuwien.foop.client;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.image.BufferedImage;

@Setter
@Getter
public class Cat {
    private int xPosition;
    private int yPosition;
    private BufferedImage ImageBuff;

    public Cat(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        loadCatImage();
    }

    private void loadCatImage() {
        var icon = new ImageIcon("public/cat/cat_back_1.png").getImage();
        ImageBuff =
                new BufferedImage(
                        icon.getWidth(null),
                        icon.getHeight(null),
                        BufferedImage.TYPE_INT_RGB
                );
        ImageBuff.createGraphics().drawImage(icon, 0, 0, null);
    }
}
