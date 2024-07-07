package at.ac.tuwien.foop.client.backend;

import java.io.BufferedReader;
import java.io.IOException;

public class GameStateListener implements Runnable {

  private BufferedReader in;
  private IClient client;
  private boolean running = true;

  public GameStateListener(BufferedReader in, IClient client) {
    this.in = in;
    this.client = client;
  }

  @Override
  public void run() {
    //Get the system time
    long lastTime = System.nanoTime();
    final double ticks = 60D;
    //Set definition of how many ticks per 1000000000 ns or 1 sec
    double ns = 1000000000 / ticks;
    double delta = 0;
    try {
      String message;
      while (running && (message = in.readLine()) != null) {
        //Update the time
        long now = System.nanoTime();
        //calculate change in time since last known time
        delta += (now - lastTime) / ns;
        //update last known time
        lastTime = now;
        //continue while delta is less than or equal to 1
        if (delta >= 1) {
          //Go through one tick
          client.receive(message);
          //decrement delta
          delta--;
        }
      }
    } catch (IOException e) {
      if (running) {
        e.printStackTrace();
      }
    }
  }

  public void stop() {
    running = false;
  }
}
