package com.example.tictactoe.commands;

import com.example.tictactoe.AppController;
import javafx.application.Platform;

public class ShowWinnerCommand extends ICommand {
    @Override
    public void execute() {
        String symbol = command.split(":")[1];

        Platform.runLater(() -> {
            AppController.getInstance().showMessage("Player " + symbol + " won!");
        });
    }
}
