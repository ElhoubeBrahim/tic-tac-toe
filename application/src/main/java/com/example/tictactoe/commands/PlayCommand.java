package com.example.tictactoe.commands;

import com.example.tictactoe.AppController;
import javafx.application.Platform;

public class PlayCommand extends ICommand {
    @Override
    public void execute() {
        String[] coordinates = command.split(" ")[1].split(",");
        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);

        Platform.runLater(() -> {
            AppController.getInstance().updateGrid(row, col);
        });
    }
}
