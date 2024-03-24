package at.ac.tuwien.foop.server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        new Server(8008);
    }
}