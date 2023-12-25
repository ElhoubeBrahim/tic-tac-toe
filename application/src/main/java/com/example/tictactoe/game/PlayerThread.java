package com.example.tictactoe.game;

import com.example.tictactoe.commands.*;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

enum Commands {
    GAME_STARTED, SET_PLAYER, PLAY, WINNER, DRAW, OPPONENT_DISCONNECTED
}

public class PlayerThread extends Thread {
    private Scanner input;

    private HashMap<String, ICommand> commands = new HashMap<>();

    PlayerThread(Socket socket) throws IOException {
        this.input = new Scanner(socket.getInputStream());
        this.setupCommands();
    }

    /**
     * Map the commands to their respective ICommand implementations
     */
    public void setupCommands() {
        commands.put(Commands.GAME_STARTED.toString(), new GameStartedCommand());
        commands.put(Commands.SET_PLAYER.toString(), new SetPlayerCommand());
        commands.put(Commands.PLAY.toString(), new PlayCommand());
        commands.put(Commands.WINNER.toString(), new ShowWinnerCommand());
        commands.put(Commands.DRAW.toString(), new ShowDrawCommand());
        commands.put(Commands.OPPONENT_DISCONNECTED.toString(), new OpponentDisconnectedCommand());
    }

    public void run() {
        while (true) {
            if (!input.hasNextLine()) {
                continue;
            }

            // Read the command from the socket input
            String command = input.nextLine();

            // Loop through the commands HashMap and check if the command is valid
            for (String key : commands.keySet()) {
                if (command.startsWith(key)) {
                    // Log command
                    System.out.println("Received command: " + command);

                    // Execute the command implementation
                    commands.get(key).setCommand(command).execute();
                    break;
                }
            }
        }
    }
}
