package at.ac.tuwien.foop.client.backend;

import at.ac.tuwien.foop.client.GameBoardPanel;
import at.ac.tuwien.foop.client.Mouse;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import org.json.JSONArray;
import org.json.JSONObject;

public class GameStateListener extends Thread {

  private boolean isRunning = true;
  Socket clientSocket;
  DataInputStream reader;
  int numberPlayers = 0;
  Mouse clientMouse;
  private static int startPositionX = 60;
  private static int startPositionY = 33;
  String username;
  GameBoardPanel boardPanel;
  Client client;

  public GameStateListener(
    Mouse clientMouse,
    String username,
    GameBoardPanel boardPanel,
    DataInputStream reader
  ) {
    this.username = username;
    this.boardPanel = boardPanel;
    this.clientMouse = clientMouse;
    this.reader = reader;
  }

  @Override
  public void run() {
    while (isRunning) {
      String sentence = "";
      JSONObject jo = null;
      JSONArray mice = null;
      int miceLength = 0;
      String lit = "{\"gameField\"";
      String end = "\"}\n{\"";
      int strStart = -1;
      int strEnd = -1;
      try {
        sentence = reader.readUTF();
        strStart = sentence.indexOf(lit);
        strEnd = sentence.indexOf(end);
        while (strStart > strEnd && strEnd != -1 && strStart != -1) {
          sentence = sentence.substring(strStart);
          strStart = sentence.indexOf(lit);
          strEnd = sentence.indexOf(end);
        }
      } catch (IOException ex) {}
      if (strEnd != -1 && strStart != -1) {
        sentence = sentence.substring(strStart, strEnd + 3);
      }

      try {
        jo = new JSONObject(sentence.toString());
        mice = jo.getJSONArray("mice");
        miceLength = mice.length();
      } catch (org.json.JSONException e) {
        System.out.println("!!!!!!!!!!!!!!!!!!JSONException " + e.getMessage());
      }
      if (mice != null) {
        if (numberPlayers < miceLength) { // registration of new players on the field
          System.out.println("registration of new players on the field");
          for (int index = 0; index < miceLength; index++) {
            int id = index;
            String name = mice.getJSONObject(index).getString("username");
            JSONArray position = new JSONArray(
              mice.getJSONObject(index).getJSONArray("position").toString()
            );
            int positionX = position.getInt(0) + startPositionX;
            int positionY = position.getInt(1) + startPositionY;
            if (name.equals(username)) {
              clientMouse.setMouseID(id);
            }
            System.out.println("registration new mouse id = " + id);
            boardPanel.registerNewMouse(new Mouse(positionX, positionY, 1, id));
          }
          numberPlayers = miceLength;
        } else {
        //   System.out.println("update players on the field");
          for (int index = 0; index < miceLength; index++) {
            String name = mice.getJSONObject(index).getString("username");
            if (!name.equals(username)) {
              int id = index;
              JSONArray position = new JSONArray(
                mice.getJSONObject(index).getJSONArray("position").toString()
              );
              int positionX = position.getInt(0) + startPositionX;
              int positionY = position.getInt(1) + startPositionY;
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
