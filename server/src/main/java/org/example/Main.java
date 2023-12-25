package org.example;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Main {
    static ArrayList<PlayerThread> players = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // Create a server socket on port 8080
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");

        while (true) {
            // Abort if there are already 2 players
            if (players.size() == 2) {
                continue;
            }

            // Accept first player connection
            PlayerThread player1 = new PlayerThread(serverSocket.accept(), Player.X);
            players.add(player1);

            // Accept second player connection
            PlayerThread player2 = new PlayerThread(serverSocket.accept(), Player.O);
            players.add(player2);

            // Start the game
            new GameController(players);
        }
    }
}