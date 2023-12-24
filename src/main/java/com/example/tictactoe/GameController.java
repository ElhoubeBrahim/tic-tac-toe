package com.example.tictactoe;

enum Player {
    X, O
}

public class GameController {
    private Player[][] board = new Player[3][3];

    private Player currentPlayer = Player.X;

    private int filledCells = 0;

    private Player winner;

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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCurrentSymbol() {
        return currentPlayer == Player.X ? "X" : "O";
    }

    public void play(int row, int col) {
        updateBoard(row, col);

        if (filledCells >= 5) {
            checkWinner();
        }
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
}
