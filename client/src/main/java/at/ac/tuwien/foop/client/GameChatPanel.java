package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.network.dto.GameStateDto;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;

public class GameChatPanel extends JPanel implements ActionListener {

  private JTextArea messageArea;
  private JTextField textField;
  private String clientId;
  private JButton tunnel1;
  private JButton tunnel2;
  private JButton tunnel3;
  private JButton tunnel4;
  private JButton tunnel5;
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
    this.textField.setBackground(Color.DARK_GRAY);

    tunnel1 = new JButton("1");
    tunnel2 = new JButton("2");
    tunnel3 = new JButton("3");
    tunnel4 = new JButton("4");
    tunnel5 = new JButton("5");

    tunnel1.addActionListener(this);
    tunnel2.addActionListener(this);
    tunnel3.addActionListener(this);
    tunnel4.addActionListener(this);
    tunnel5.addActionListener(this);

    this.tunnel1.setEnabled(false);
    this.tunnel2.setEnabled(false);
    this.tunnel3.setEnabled(false);
    this.tunnel4.setEnabled(false);
    this.tunnel5.setEnabled(false);

    JPanel bottomPanel = new JPanel();

    bottomPanel.add(tunnel1);
    bottomPanel.add(tunnel2);
    bottomPanel.add(tunnel3);
    bottomPanel.add(tunnel4);
    bottomPanel.add(tunnel5);
    add(bottomPanel);
  }

  public void updateBoard(GameStateDto gameState) {
    startVote = false;
    this.textField.setEditable(false);
    this.textField.setBackground(Color.DARK_GRAY);
    this.tunnel1.setEnabled(false);
    this.tunnel2.setEnabled(false);
    this.tunnel3.setEnabled(false);
    this.tunnel4.setEnabled(false);
    this.tunnel5.setEnabled(false);

    var mice = gameState.mice();
    for (var mouse : mice) {
      String id = mouse.clientId().toString();
      var clientMouse = this.mice.get(id);
      // Add mouse if not already encountered
      if (clientMouse == null) {
        clientMouse =
          new Mouse(
            id,
            (int) mouse.position()[0],
            (int) mouse.position()[1],
            1,
            mouse.level(),
            mouse.tunnelVote()
          );
        this.mice.put(id, clientMouse);
      } else {
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
      }
    }
    if (startVote) {
      this.textField.setEditable(true);
      this.textField.setBackground(Color.WHITE);
      this.tunnel1.setEnabled(true);
      this.tunnel2.setEnabled(true);
      this.tunnel3.setEnabled(true);
      this.tunnel4.setEnabled(true);
      this.tunnel5.setEnabled(true);
      for (var mouse : mice) {
        int playerTunnel = this.mice.get(clientId).getTunnel();
        String id = mouse.clientId().toString();
        // update level mouse
        this.mice.get(id).setTunnel(mouse.level());

        //chat only for mice in one tunnel
        if (mouse.level() == playerTunnel) {
          Integer vote = this.mice.get(id).getTunnelVote();
          Integer sVote = mouse.tunnelVote();
          if (sVote != vote) {
            this.messageArea.append(mouse.username() + ": " + sVote + "\n");
            this.messageArea.append("\n");
            // update vote mouse
            this.mice.get(id).setTunnelVote(sVote);
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

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (obj == tunnel1) {
      this.textField.setText("1");
    }
    if (obj == tunnel2) {
      this.textField.setText("2");
    }
    if (obj == tunnel3) {
      this.textField.setText("3");
    }
    if (obj == tunnel4) {
      this.textField.setText("4");
    }
    if (obj == tunnel5) {
      this.textField.setText("5");
    }
  }
}
