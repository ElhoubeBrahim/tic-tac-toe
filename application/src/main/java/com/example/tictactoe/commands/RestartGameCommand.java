package com.example.tictactoe.commands;

import com.example.tictactoe.AppController;
import javafx.application.Platform;

public class RestartGameCommand extends ICommand {
    public void execute() {
        Platform.runLater(() -> {
            AppController.getInstance().restartGame();
        });
    }
}
