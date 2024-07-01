package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.client.backend.Client;
import at.ac.tuwien.foop.client.backend.InputManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.*;

public class ClientGUI
  extends JFrame
  implements ActionListener, WindowListener {

  /** Creates a new instance of ClientGUI */
  private JLabel ipaddressLabel;
  private JLabel usernameLabel;
  private JLabel portLabel;

  private JTextField ipaddressText;
  private JTextField usernameText;
  private JTextField portText;

  private JButton registerButton;

  private JButton readyButton;

  private JPanel registerPanel;
  public static JPanel gameStatusPanel;
  private Client client;
  private Mouse clientMouse;

  int width = 1400, height = 910;
  boolean allClientsReady = true;
  private GameBoardPanel boardPanel;

  private String host;
  private int port;
  private String username;

  public ClientGUI() {
    setTitle("Mice and Cats in a Network Game");
    setSize(width, height);
    setLocation(60, 100);
    getContentPane().setBackground(Color.BLACK);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    addWindowListener(this);
    registerPanel = new JPanel();
    registerPanel.setBackground(Color.YELLOW);
    registerPanel.setSize(400, 200);
    registerPanel.setBounds(850, 35, 400, 200);
    registerPanel.setLayout(null);

    gameStatusPanel = new JPanel();
    gameStatusPanel.setBackground(Color.YELLOW);
    gameStatusPanel.setSize(400, 300);
    gameStatusPanel.setBounds(850, 260, 400, 573);
    gameStatusPanel.setLayout(null);

    ipaddressLabel = new JLabel("IP address: ");
    ipaddressLabel.setBounds(90, 25, 100, 25);

    portLabel = new JLabel("Port: ");
    portLabel.setBounds(90, 55, 70, 25);

    usernameLabel = new JLabel("Username: ");
    usernameLabel.setBounds(90, 85, 70, 25);

    ipaddressText = new JTextField("localhost");
    ipaddressText.setBounds(210, 25, 100, 25);

    portText = new JTextField("8008");
    portText.setBounds(210, 55, 100, 25);

    usernameText = new JTextField("User1");
    usernameText.setBounds(210, 85, 100, 25);

    registerButton = new JButton("Register");
    registerButton.setBounds(90, 130, 220, 25);

    registerButton.addActionListener(this);
    registerButton.setFocusable(true);

    readyButton = new JButton("Ready");
    readyButton.setBounds(90, 160, 220, 25);

    readyButton.addActionListener(this);
    readyButton.setEnabled(false);

    registerPanel.add(ipaddressLabel);
    registerPanel.add(portLabel);
    registerPanel.add(usernameLabel);
    registerPanel.add(ipaddressText);
    registerPanel.add(portText);
    registerPanel.add(usernameText);
    registerPanel.add(registerButton);
    registerPanel.add(readyButton);

    boardPanel = new GameBoardPanel(60, 33, 750, 800);

    // FIXME: this could be cleaned up
    client = new Client(boardPanel);
    var inputManager = new InputManager(client);
    boardPanel.addKeyListener(inputManager);

    getContentPane().add(registerPanel);
    getContentPane().add(gameStatusPanel);
    getContentPane().add(boardPanel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    host = ipaddressText.getText();
    port = Integer.parseInt(portText.getText());
    username = usernameText.getText();
    if (obj == registerButton) {
      registerButton.setEnabled(false);
      readyButton.setFocusable(false);
      try {
        client.connect(host, port);
        try {
          Thread.sleep(500);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        registerButton.setFocusable(false);
        readyButton.setFocusable(true);
        readyButton.setEnabled(true);
        client.register(username);
        try {
          Thread.sleep(500);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      } catch (UnknownHostException ex) {
        System.out.println("Unknown host: " + host);
      } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
          this,
          "The Server is not running, try again later!",
          "Mice and Cats in a Network Game",
          JOptionPane.INFORMATION_MESSAGE
        );
        System.out.println("The Server is not running!");
        registerButton.setEnabled(true);
        readyButton.setEnabled(false);
      }
    }
    if (obj == readyButton) {
      readyButton.setEnabled(false);
      try {
        client.initiateReady(username);
        try {
          Thread.sleep(500);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      } catch (RuntimeException ex) {
        JOptionPane.showMessageDialog(
          this,
          "The Server is not handshaking, try again later!",
          "Mice and Cats in a Network Game",
          JOptionPane.INFORMATION_MESSAGE
        );
        System.out.println("The Server is not ready Handshake!");
        readyButton.setEnabled(true);
      }
      try {
        Thread.sleep(2000);
        if (allClientsReady) {
          boardPanel.repaint();
          boardPanel.setFocusable(true);
        } else {
          JOptionPane.showMessageDialog(
            this,
            "The players are not ready, try again later!",
            "Mice and Cats in a Network Game",
            JOptionPane.INFORMATION_MESSAGE
          );
          System.out.println("all Clients are not ready!");
          readyButton.setEnabled(true);
        }
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }

  public void windowOpened(WindowEvent e) {}

  public void windowClosing(WindowEvent e) {
    JOptionPane.showConfirmDialog(
      this,
      "Are you sure you want to exit ?",
      "Mice and Cats in a Network Game!",
      JOptionPane.YES_NO_OPTION
    );
  }

  public void windowClosed(WindowEvent e) {}

  public void windowIconified(WindowEvent e) {}

  public void windowDeiconified(WindowEvent e) {}

  public void windowActivated(WindowEvent e) {}

  public void windowDeactivated(WindowEvent e) {}
}
