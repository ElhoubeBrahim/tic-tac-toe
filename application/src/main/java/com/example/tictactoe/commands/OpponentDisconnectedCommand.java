package com.example.tictactoe.commands;

import com.example.tictactoe.AppController;
import javafx.application.Platform;

public class OpponentDisconnectedCommand extends ICommand {
    @Override
    public void execute() {
        Platform.runLater(() -> {
            AppController.getInstance().showMessage("Opponent disconnected!");
        });
    }
}