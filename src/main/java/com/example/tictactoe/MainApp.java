package com.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load view
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("views/game.fxml"));
        Image icon = new Image(MainApp.class.getResourceAsStream("images/logo.png"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set stage properties
        stage.setTitle("Tic Tac Toe");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}