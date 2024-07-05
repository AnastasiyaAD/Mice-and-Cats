package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class GameChatPanel extends JPanel {

  private JTextArea messageArea;
  private JTextField textField;
  private String clientId;
  public boolean startVote;
  private HashMap<String, Mouse> mice = new HashMap<>();

  public GameChatPanel(int x, int y, int width, int height) {
    setSize(width, height);
    setBounds(x, y, width, height);
    setFocusable(true);
    startVote = false;
    messageArea = new JTextArea();
    messageArea.setEditable(false);
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    messageArea.setFont(new Font("Dialog", Font.PLAIN, 14));

    var scroll = new JScrollPane(
      messageArea,
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
    );
    Dimension dimField = new Dimension();
    dimField.setSize(new Dimension(width - 30, height - 120));
    scroll.setPreferredSize(dimField);
    add(scroll);

    textField = new JTextField();
    Dimension dimFieldText = new Dimension();
    dimFieldText.setSize(new Dimension(width, 30));
    textField.setPreferredSize(dimFieldText);
    textField.setLocation(x, y + height - 60);
    add(textField);
    textField.setEditable(false);
  }

  public void updateBoard(GameStateDto gameState) {
    startVote = false;
    this.textField.setEditable(false);

    var mice = gameState.mice();

    for (var mouse : mice) {
      String id = mouse.clientId().toString();
      var clientMouse = this.mice.get(id);
      // Add mouse if not already encountered
      if (clientMouse == null) {
        clientMouse = new Mouse();
        this.mice.put(id, clientMouse);
      }
      if (id.equals(clientId)) {
        //the player has entered the tunnel, we begin voting
        if (mouse.level() != 0) {
          startVote = true;
        }
        //the player has changed the tunnel, clearing the voting history
        if (mouse.level() != clientMouse.getTunnel()) {
          this.messageArea.setText("");
        }
      }
      clientMouse.setTunnel((int) mouse.level());
    }
    if (startVote) {
      this.textField.setEditable(true);
      for (var mouse : mice) {
        String id = mouse.clientId().toString();
        var clientMouse = this.mice.get(id);
        int playerTunnel = clientMouse.getTunnel();

        //chat only for mice in one tunnel
        if (mouse.level() == playerTunnel) {
          Integer vote = clientMouse.getTunnelVote();
          Integer sVote = mouse.tunnelVote();
          if (sVote != vote) {
            this.messageArea.append(mouse.username() + ": " + sVote + "\n");
            this.messageArea.append("\n");
            // update vote mouse
            clientMouse.setTunnelVote(sVote);
          }
        }
      }
    }
    this.repaint();
  }

  public String getMessage() {
    return textField.getText();
  }

  public void cleanTextField() {
    textField.setText("");
  }

  public void add(String clientID) {
    clientId = clientID;
  }
}
