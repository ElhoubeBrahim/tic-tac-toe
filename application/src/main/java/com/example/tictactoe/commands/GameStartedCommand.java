package com.example.tictactoe.commands;

import com.example.tictactoe.AppController;
import javafx.application.Platform;

public class GameStartedCommand extends ICommand {
    @Override
    public void execute() {
        Platform.runLater(() -> {
            AppController.getInstance().showMessage("Game started!");
        });
    }
}
