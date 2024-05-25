package com.example.tryout_kl1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showScene("dashboard.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlFile));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Parent loadFXML(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlFile));
        return loader.load();
    }

    // Example usage
}
