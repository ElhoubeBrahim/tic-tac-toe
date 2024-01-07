package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

enum Commands {
    PLAY, QUIT
}

enum Responses {
    GAME_STARTED, SET_PLAYER, WINNER, DRAW, INVALID_COMMAND, OPPONENT_DISCONNECTED, RESTART_GAME
}

public class PlayerThread extends Thread {

    private GameController gameController;

    private final Scanner input;
    private final PrintWriter output;

    private final Player player;

    private PlayerThread opponent;

    public PlayerThread(Socket socket, Player player) throws IOException {
        // Create input and output streams
        this.input = new Scanner(socket.getInputStream());
        this.output = new PrintWriter(socket.getOutputStream(), true);

        // Set player and notify the player of their symbol
        this.player = player;
        sendMessage(Responses.SET_PLAYER + ":" + player);

        // Log player connection
        System.out.println("Player " + player + " connected");
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setOpponent(PlayerThread opponent) {
        if (this.opponent != null && this.opponent.getPlayer() == this.player) {
            return;
        }

        this.opponent = opponent;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void run() {

        try {
            while (true) {
                // If game not started yet or no input, continue
                if (opponent == null || !input.hasNextLine()) {
                    continue;
                }

                // Read the command from the socket input
                String command = input.nextLine();

                if (command.equals(Commands.QUIT.toString())) { // QUIT
                    sendToOpponent(Responses.OPPONENT_DISCONNECTED.toString());
                    System.out.println("Player " + player + " disconnected");
                    return;
                } else if (command.startsWith(Commands.PLAY.toString())) { // PLAY 1,2
                    // Get target cell coordinates
                    String[] coordinates = command.split(" ")[1].split(",");
                    int row = Integer.parseInt(coordinates[0]);
                    int col = Integer.parseInt(coordinates[1]);

                    // Fill the cell and check if the game is over
                    gameController.play(row, col, this);
                    sendToAll(command); // Update board UI for both players

                    // If the game is over, notify players
                    if (gameController.isOver()) {
                        sendToAll(
                            gameController.isDraw()
                                ? Responses.DRAW.toString()
                                : Responses.WINNER + ":" + gameController.getWinner()
                        );

                        // Restart game after 5 seconds
                        (new Timer()).schedule(new TimerTask() {
                            @Override
                            public void run() {
                                gameController.restart();
                                sendToAll(Responses.RESTART_GAME.toString());
                            }
                        }, 5 * 1000);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            // If the command is invalid, notify the player
            sendMessage(Responses.INVALID_COMMAND + ":" + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        // Log message
        // System.out.println("Sending message: " + message);
        output.println(message);
    }

    private void sendToOpponent(String message) {
        opponent.sendMessage(message);
    }

    private void sendToAll(String message) {
        sendMessage(message);
        sendToOpponent(message);
    }
}
