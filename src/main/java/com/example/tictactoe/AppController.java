package com.example.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AppController {
    private GameController game = new GameController();

    @FXML
    private GridPane boardGrid;

    @FXML
    private VBox playerX;

    @FXML
    private VBox playerO;

    @FXML
    private Label winnerLabel;

    public void handleGridClick(MouseEvent event) {
        // Stop interaction if game is over
        if (game.isOver()) {
            return;
        }

        // Get row and column index of the clicked cell
        Node cell = (Node) event.getTarget();
        Integer row = GridPane.getRowIndex(cell);
        Integer col = GridPane.getColumnIndex(cell);

        // Cell is not in the grid or already filled
        if (row == null || col == null || game.isCellFilled(row, col)) {
            return;
        }

        // Update board
        game.play(row, col);
        Label label = (Label) cell;
        label.setText(game.getCurrentSymbol());

        // Check winner
        if (game.isOver()) {
            if (game.isDraw()) {
                winnerLabel.setText("Draw!");
            } else {
                winnerLabel.setText(game.getWinner() + " wins!");
            }

            winnerLabel.setVisible(true);
            return;
        }

        // Toggle player
        togglePlayerUI();
        game.togglePlayer();
    }

    public void togglePlayerUI() {
        Player currentPlayer = game.getCurrentPlayer();

        if (currentPlayer == Player.X) {
            playerX.getStyleClass().remove("active-player");
            playerO.getStyleClass().add("active-player");
        } else {
            playerO.getStyleClass().remove("active-player");
            playerX.getStyleClass().add("active-player");
        }
    }

    public void resetGame() {
        // Reset board
        game = new GameController();

        // Reset cells
        for (Node cell : boardGrid.getChildren()) {
            try {
                Label label = (Label) cell;
                label.setText("");
            } catch (ClassCastException e) {}
        }

        // Reset player
        playerO.getStyleClass().remove("active-player");
        playerX.getStyleClass().remove("active-player"); // Remove all active-player classes
        playerX.getStyleClass().add("active-player");

        // Reset winner label
        winnerLabel.setVisible(false);
    }
}