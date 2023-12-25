package com.example.tictactoe.commands;

import com.example.tictactoe.game.GameController;
import com.example.tictactoe.game.Player;

public class SetPlayerCommand extends ICommand {
    @Override
    public void execute() {
        String player = command.split(":")[1];
        GameController.getInstance().setPlayer(player.equals("X") ? Player.X : Player.O);
    }
}
