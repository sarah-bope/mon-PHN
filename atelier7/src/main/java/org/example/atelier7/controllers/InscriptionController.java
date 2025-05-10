package org.example.atelier7.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.atelier7.Client;
import org.example.atelier7.dbManager.dbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.example.atelier7.service.Viewloader;
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

public class InscriptionController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleInscription() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("⚠️ Remplissez tous les champs.");
            return;
        }

        Client nouveauClient = new Client(username, password);
        if (nouveauClient.enregistrerUtilisateur()) {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("✅ Inscription réussie !");
        } else {
            messageLabel.setText("❌ Erreur lors de l'inscription.");
        }
    }
}


