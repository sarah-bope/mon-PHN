package org.example.atelier7.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.atelier7.GestionProduits;
import org.example.atelier7.service.Viewloader;
import java.sql.Connection;
import org.example.atelier7.dbManager.dbManager;
import java.sql.Connection;
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



public class ModifierStockController {

    @FXML
    private TextField idField;

    @FXML
    private TextField quantiteField;

    @FXML
    private Label messageLabel;

    private final GestionProduits gestionProduits = new GestionProduits();

    @FXML
    private void handleModifierStock() {
        try {
            int id = Integer.parseInt(idField.getText());
            int quantite = Integer.parseInt(quantiteField.getText());

            boolean success = gestionProduits.modifierStockParId(id, quantite);

            if (success) {
                messageLabel.setText("✅ Stock mis à jour !");
            } else {
                messageLabel.setText("❌ Produit introuvable.");
            }

        } catch (NumberFormatException e) {
            messageLabel.setText("⚠️ ID ou quantité invalide.");
        }
    }
}
