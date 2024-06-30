package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import lombok.Setter;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GameBoardPanel extends JPanel {

    /**
     * Creates a new instance of GameBoardPanel
     */
    private int width = 800;
    private int height = 850;
    // FIXME: Why is this static?
    private HashMap<String, Mouse> mice;
    int z = 0;
    int y = 1;
    int k = 0;
    int l = 1;

    @Setter
    private String username;

    public GameBoardPanel() {
        setSize(width, height);
        setBounds(0, 0, width + 30, height + 20);
        setFocusable(true);
        mice = new HashMap<>();
    }

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
        this.repaint();
    }

    private void drawUnderground(Graphics2D g, int tunnel) {
        g.drawImage(
                new ImageIcon(
                        MessageFormat.format("public/playing_field/tunnel_{0}.png", tunnel)
                )
                        .getImage(),
                // FIXME: Why?
                60,
                33,
                null
        );
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

        repaint();
    }
}
