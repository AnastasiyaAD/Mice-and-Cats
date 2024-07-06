package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class GameChatPanel extends JPanel {

  private JTextPane messageArea;
  private JTextField textField;
  private String clientId;
  public boolean startVote;
  private HashMap<String, Mouse> mice = new HashMap<>();

  public GameChatPanel(int x, int y, int width, int height) {
    setSize(width, height);
    setBounds(x, y, width, height);
    setFocusable(true);
    startVote = false;
    messageArea = new JTextPane();
    messageArea.setEditable(false);

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

        //chat only for mice in one tunnel without messages from the player himself
        if (mouse.level() == playerTunnel && !id.equals(clientId)) {
          Integer vote = clientMouse.getTunnelVote();
          Integer sVote = mouse.tunnelVote();
          if (sVote != vote) {
            appendToPane(
              this.messageArea,
              mouse.username() + ": " + sVote + "\n" + "\n",
              Color.BLACK
            );
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

  public void sendMessage(Integer text) {
    appendToPane(this.messageArea, text + "\n" + "\n", Color.RED);
    cleanTextField();
  }

  private void appendToPane(JTextPane tp, String msg, Color c) {
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(
      SimpleAttributeSet.EMPTY,
      StyleConstants.Foreground,
      c
    );

    aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
    aset =
      sc.addAttribute(
        aset,
        StyleConstants.Alignment,
        StyleConstants.ALIGN_JUSTIFIED
      );

    int len = tp.getDocument().getLength();
    StyledDocument doc = (StyledDocument) tp.getDocument();
    try {
      doc.insertString(len, msg, aset);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }
}
