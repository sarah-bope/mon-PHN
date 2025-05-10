package org.example.atelier7.controllers;

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
public class AjoutProduitController {

    @FXML private TextField nomField;
    @FXML private TextField prixField;
    @FXML private TextField stockField;
    @FXML private ComboBox<String> categorieCombo;

    @FXML
    private void initialize() {
        // Initialiser les valeurs de la combo si nécessaire (sinon déjà fait dans le FXML)
        if (categorieCombo.getItems().isEmpty()) {
            categorieCombo.getItems().addAll("ordinateur", "telephone", "accessoire");
        }
    }

    @FXML
    private void handleAjouter() {
        String nom = nomField.getText().trim();
        String prixText = prixField.getText().trim();
        String stockText = stockField.getText().trim();
        String categorie = categorieCombo.getValue();

        if (nom.isEmpty() || prixText.isEmpty() || stockText.isEmpty() || categorie == null) {
            showAlert(Alert.AlertType.ERROR, "Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }

        double prix;
        int stock;

        try {
            prix = Double.parseDouble(prixText);
            stock = Integer.parseInt(stockText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Format invalide", "Le prix ou le stock n'est pas au bon format.");
            return;
        }

        try (Connection conn = dbManager.getConnection()) {
            String sql = "INSERT INTO produits (nom, prix, quantite, categorie) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nom);
                stmt.setDouble(2, prix);
                stmt.setInt(3, stock);
                stmt.setString(4, categorie);
                stmt.executeUpdate();
            }
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit ajouté avec succès.");
            closeWindow();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du produit : " + e.getMessage());
        }
    }

    @FXML
    private void handleAnnuler() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
