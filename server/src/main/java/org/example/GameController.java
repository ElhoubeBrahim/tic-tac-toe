package org.example;

import java.util.ArrayList;

enum Player {
    X, O
}

enum ExceptionTypes {
    NOT_YOUR_TURN, CELL_FILLED, GAME_OVER
}

public class GameController {
    private Player[][] board = new Player[3][3];

    private int filledCells = 0;

    private Player currentPlayer = Player.X;

    private Player winner;

    GameController(ArrayList<PlayerThread> players) {
        PlayerThread player1 = players.get(0);
        PlayerThread player2 = players.get(1);

        // Assign players to the game
        player1.setGameController(this);
        player2.setGameController(this);

        // Assign opponents to each player
        player1.setOpponent(player2);
        player2.setOpponent(player1);

        // Start the game
        player1.start();
        player2.start();

        // Notify players that the game has started
        player1.sendMessage(Responses.GAME_STARTED.toString());
        player2.sendMessage(Responses.GAME_STARTED.toString());
    }

    public boolean isOver() {
        return filledCells == 9 || winner != null;
    }

    public boolean isDraw() {
        return filledCells == 9 && winner == null;
    }

    public boolean isCellFilled(int row, int col) {
        return board[row][col] != null;
    }

    public Player getWinner() {
        return winner;
    }

    public void updateBoard(int row, int col) {
        board[row][col] = currentPlayer;
        filledCells++;
    }

    public void checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) { // Check rows
                winner = board[i][0];
                return;
            } else if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) { // Check columns
                winner = board[0][i];
                return;
            }
        }

        // Check diagonals
        if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0];
        } else if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[0][2];
        }
    }

    public void togglePlayer() {
        if (isOver()) {
            return;
        }

        currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }

    public void play(int row, int col, PlayerThread playerThread) {
        if (playerThread.getPlayer() != currentPlayer) { // If it's not your turn
            throw new IllegalArgumentException(ExceptionTypes.NOT_YOUR_TURN.toString());
        } else if (isCellFilled(row, col)) { // If the cell is already filled
            throw new IllegalArgumentException(ExceptionTypes.CELL_FILLED.toString());
        } else if (isOver()) { // If the game is over
            throw new IllegalArgumentException(ExceptionTypes.GAME_OVER.toString());
        }

        // Log the move
        System.out.println("Player " + currentPlayer + " played " + row + "," + col);

        // Update board and check for winner
        updateBoard(row, col);
        if (filledCells >= 5) {
            checkWinner();
        }

        togglePlayer();
    }

    public void restart() {
        filledCells = 0;
        currentPlayer = Player.X;
        board = new Player[3][3];
        winner = null;
    }
}
