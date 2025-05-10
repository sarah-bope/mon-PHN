package org.example.atelier7.controllers;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.sql.Connection;
import org.example.atelier7.dbManager.dbManager;
import org.example.atelier7.service.Viewloader;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import javafx.scene.control.*;
import org.example.atelier7.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.atelier7.dbManager.dbManager;
import org.example.atelier7.service.Viewloader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class GestionUtilisateursController {

    @FXML
    private void handleAjouterUtilisateur(ActionEvent event) {
        showAlert("Ajout utilisateur", "Fonctionnalité à implémenter : ajout d’un utilisateur.");
        // Tu peux ouvrir une nouvelle fenêtre ou formulaire d'inscription ici
    }

    @FXML
    private void handleListerUtilisateurs(ActionEvent event) {
        showAlert("Liste des utilisateurs", "Fonctionnalité à implémenter.");
        // Tu peux afficher une nouvelle scène avec TableView, par exemple
    }

    @FXML
    private void handleRetour(ActionEvent event) {
        // Ferme la fenêtre actuelle
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
