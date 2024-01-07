package com.example.tictactoe;

import com.example.tictactoe.game.GameController;
import com.example.tictactoe.game.Player;
import com.example.tictactoe.game.PlayerThread;
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

    private static AppController instance;

    public AppController() {
    	instance = this;
    }

    public void handleGridClick(MouseEvent event) {
        // Get row and column index of the clicked cell
        Node cell = (Node) event.getTarget();
        Integer row = GridPane.getRowIndex(cell);
        Integer col = GridPane.getColumnIndex(cell);

        // Cell is not in the grid or already filled
        if (row == null || col == null) {
            return;
        }

        game.play(row, col);
    }

    public void updateGrid(int row, int col) {
        // Get cell at row and column from the grid
        Node cell = boardGrid.getChildren().get(row * 3 + col);
        Label label = (Label) cell;

        // Update board
        label.setText(game.getCurrentSymbol());

        // Toggle player
        togglePlayerUI();
        game.togglePlayer();
    }

    public void restartGame() {
        // Reset player
        if (game.getCurrentPlayer() == Player.O) {
            togglePlayerUI();
            game.setCurrentPlayer(Player.X);
        }

        // Reset grid
        for (Node child : boardGrid.getChildren()) {
            Integer column = GridPane.getColumnIndex(child);
            Integer row = GridPane.getRowIndex(child);

            if (column != null && row != null) {
                ((Label) child).setText("");
            }
        }

        // Show message
        showMessage("Game started !");
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

    public void close() {
    	game.disconnect();
    }

    public void showMessage(String message) {
    	winnerLabel.setText(message);
        winnerLabel.setVisible(true);
    }

    public static AppController getInstance() {
    	return instance;
    }
}