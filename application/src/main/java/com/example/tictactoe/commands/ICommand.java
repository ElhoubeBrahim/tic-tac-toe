package com.example.tictactoe.commands;

public abstract class ICommand {
    protected String command;

    public ICommand setCommand(String command) {
        this.command = command;
        return this;
    }

    public abstract void execute();
}
