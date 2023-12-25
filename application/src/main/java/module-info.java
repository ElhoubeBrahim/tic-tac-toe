module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.tictactoe to javafx.fxml;
    exports com.example.tictactoe;
    exports com.example.tictactoe.game;
    opens com.example.tictactoe.game to javafx.fxml;
}