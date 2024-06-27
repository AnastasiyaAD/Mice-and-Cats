package at.ac.tuwien.foop.client;

import at.ac.tuwien.foop.client.backend.Client;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONObject;

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
  private int numberPlayers = 0;

  int width = 1400, height = 910;
  boolean isRunning = true;
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
    registerPanel.setBounds(880, 40, 400, 200);
    registerPanel.setLayout(null);

    gameStatusPanel = new JPanel();
    gameStatusPanel.setBackground(Color.YELLOW);
    gameStatusPanel.setSize(400, 300);
    gameStatusPanel.setBounds(880, 280, 400, 573);
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

    client = Client.getGameClient();
    clientMouse = new Mouse();
    boardPanel = new GameBoardPanel(clientMouse, false);

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
        new ClientReceivingThread(client.getSocket()).start(); //start Listening
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
      } catch (IOException ex) {
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
          boardPanel.setGameStatus(true);
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

  public class ClientReceivingThread extends Thread {

    Socket clientSocket;
    DataInputStream reader;

    public ClientReceivingThread(Socket clientSocket) {
      this.clientSocket = clientSocket;
      try {
        reader = new DataInputStream(clientSocket.getInputStream());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    public void run() {
      while (isRunning) {
        String sentence = "no";
        JSONObject jo = null;
        JSONArray mice = null;
        int miceLength = 0;
        try {
          sentence = reader.readUTF();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        try {
          jo = new JSONObject(sentence.toString());
          mice = jo.getJSONArray("mice");
          miceLength = mice.length();
        } catch (org.json.JSONException e) {
          // TODO: handle exception
        }

        System.out.println(jo);

        if (mice != null) {
          if (numberPlayers < miceLength) { // registration of new players on the field
            System.out.println("registration of new players on the field");
            for (int index = 0; index < miceLength; index++) {
              int id = index;
              String name = mice.getJSONObject(index).getString("username");
              JSONArray position = new JSONArray(
                mice.getJSONObject(index).getJSONArray("position").toString()
              );
              System.out.println("position = " + position);

              int positionX = position.getInt(0);
              int positionY = position.getInt(1);
              if (name.equals(username)) {
                clientMouse.setMouseID(id);
              }
              System.out.println("registration new mouse id = " + id);

              System.out.println(
                "positionX = " + positionX + " positionY = " + positionY
              );
              boardPanel.registerNewMouse(
                new Mouse(positionX, positionY, 1, id)
              );
            }
            numberPlayers = miceLength;
          } else {
            System.out.println("update players on the field");
            for (int index = 0; index < miceLength; index++) {
              String name = mice.getJSONObject(index).getString("username");

              int id = index;
              JSONArray position = new JSONArray(
                mice.getJSONObject(index).getJSONArray("position").toString()
              );
              int positionX = position.getInt(0);
              int positionY = position.getInt(1);
              System.out.println(
                "Update Mouse ID = " +
                id +
                " positionX = " +
                positionX +
                " positionY = " +
                positionY
              );
              try {
                boardPanel.getMouse(id).setXpoistion(positionX);
                boardPanel.getMouse(id).setYposition(positionY);
              } catch (Exception e) {
                System.out.println(
                  "!!!!!!!!Exception in Update Mouse!!!!!!!!: " + e.getMessage()
                );
              }
            }
            boardPanel.repaint();
          }
        }
      }
      try {
        client.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
