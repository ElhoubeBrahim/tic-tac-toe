package com.example.tictactoe.game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class GameController {
    private static GameController instance;

    private final PrintWriter output;

    private Player player;

    private Player currentPlayer = Player.X;

    public GameController() {
        instance = this;

        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 8080);
            this.output = new PrintWriter(socket.getOutputStream(), true);

            // Create new thread to listen for server commands
            new PlayerThread(socket).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void play(int row, int col) {
        if (player != currentPlayer) { // Not your turn
            return;
        }

        // Send command to server
        output.println("PLAY " + row + "," + col);
    }

    public void disconnect() {
        // Send command to server
        output.println("QUIT");
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getCurrentSymbol() {
        return currentPlayer == Player.X ? "X" : "O";
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void togglePlayer() {
        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    public static GameController getInstance() {
        return instance;
    }
}
