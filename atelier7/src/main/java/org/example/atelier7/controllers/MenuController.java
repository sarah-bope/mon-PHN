package org.example.atelier7.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.example.atelier7.service.Viewloader;
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


public class MenuController {

    @FXML
    private Button connexionButton;

    @FXML
    private void handleConnexion() {
        Viewloader.start("admin_menu.fxml", "Menu Administrateur");
    }

    @FXML
    private void handleInscription() {
        Viewloader.start("inscription.fxml", "Inscription");
    }

    @FXML
    private void handleAfficherProduitsParCategorie() {
        Viewloader.start("produits_par_categorie.fxml", "Produits par Catégorie");
    }

    @FXML
    private void handleSupprimerProduit() {
        Viewloader.start("SupprimerProduitView.fxml", "Supprimer un produit");
    }

    @FXML
    private void handleModifierStock() {
        Viewloader.start("ModifierProduit.fxml", "Modifier un produit");

    }
    @FXML
    private void handleListerProduits() {
        Viewloader.start("AfficherProduitsView.fxml", "Liste des Produits");
    }


    @FXML
    private void handleAjouterUtilisateur() {
        Viewloader.start("ajouter_utilisateur.fxml", "Ajouter un utilisateur");
    }

    @FXML
    private void handleListerUtilisateurs() {
        Viewloader.start("liste_utilisateurs.fxml", "Liste des utilisateurs");
    }

    @FXML
    private void handleRetour() {
        Viewloader.start("menu.fxml", "Accueil");
    }

    @FXML
    private void handleQuitter() {
        Platform.exit();
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
