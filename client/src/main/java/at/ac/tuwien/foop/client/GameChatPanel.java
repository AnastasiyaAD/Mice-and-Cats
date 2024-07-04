package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import java.awt.*;
import javax.swing.*;

public class GameChatPanel extends JPanel {

  private JTextArea messageArea;
  private JTextField textField;
  private String clientId;

  public GameChatPanel(int x, int y, int width, int height) {
    setSize(width, height);
    setBounds(x, y, width, height);
    setFocusable(true);

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
    dimField.setSize(new Dimension(width - 30, height - 80));
    scroll.setPreferredSize(dimField);
    add(scroll);

    textField = new JTextField();
    Dimension dimFieldText = new Dimension();
    dimFieldText.setSize(new Dimension(width - 100, 30));
    textField.setPreferredSize(dimFieldText);
    textField.setLocation(x, y + height - 60);
    add(textField);
    textField.setEditable(false);
    this.textField.setBackground(Color.DARK_GRAY);
  }

  public void updateBoard(GameStateDto gameState) {
    var mice = gameState.mice();
    this.textField.setEditable(false);
    this.textField.setBackground(Color.DARK_GRAY);
    for (var mouse : mice) {
      String id = mouse.clientId().toString();
      if (id.equals(clientId) && mouse.level() != 0) {
        this.textField.setEditable(true);
        this.textField.setBackground(Color.WHITE);
      }
    }
    //TODO: append server message
    // this.messageArea.append(gameState.status().toString() + "\n")
    // this.messageArea.append("\n");
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
