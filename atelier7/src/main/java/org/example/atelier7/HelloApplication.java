package org.example.atelier7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.atelier7.dbManager.dbManager;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Initialisation de la base de données : création des tables si elles n'existent pas
        dbManager.initialiser();

        // Chargement du fichier FXML de la vue principale
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600); // taille ajustée pour une UI standard
        stage.setTitle("Gestion de stock");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
