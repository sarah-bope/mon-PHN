package org.example.atelier7.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.atelier7.HelloApplication;

import java.io.IOException;

public class Viewloader {

    public static void start(String vuePath, String titre) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(vuePath));
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            Stage stage = (Stage) Window.getWindows().stream()
                    .filter(Window::isShowing)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Aucune fenêtre visible"));
            stage.setTitle(titre);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
