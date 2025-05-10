package org.example.atelier7.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.atelier7.GestionProduits;
import org.example.atelier7.Produit;
import org.example.atelier7.dbManager.dbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.example.atelier7.service.Viewloader;

public class SupprimerProduitController {

    @FXML
    private TextField idField;

    @FXML
    private Label messageLabel;

    private final GestionProduits gestionProduits = new GestionProduits();

    @FXML
    public void handleSupprimer() {
        try {
            int id = Integer.parseInt(idField.getText());

            // Vérifie si le produit existe
            Produit produit = gestionProduits.getProduits().stream()
                    .filter(p -> p.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (produit != null) {
                gestionProduits.supprimerProduitParId(id); // appel à la bonne méthode
                messageLabel.setText("✅ Produit supprimé.");
            } else {
                messageLabel.setText("❌ Produit introuvable.");
            }

        } catch (NumberFormatException e) {
            messageLabel.setText("⚠️ ID invalide.");
        }
    }
}
