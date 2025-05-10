package org.example.atelier7.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.atelier7.service.Viewloader;

import java.io.IOException;

public class AdminMenuController {

    @FXML
    private void handleGererUtilisateurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/atelier7/gestion_utilisateurs.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestion des utilisateurs");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la vue de gestion des utilisateurs.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGererProduits() {
        Viewloader.start("gestion_produits.fxml", "gsp");
    }



    @FXML
    private void handleDeconnexion() {
        Platform.exit(); // Tu peux ici retourner à une autre scène plutôt que quitter l'app
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
