package org.example.atelier7.controllers;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import  org.example.atelier7.GestionProduits;
import javafx.fxml.FXMLLoader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import org.example.atelier7.service.Viewloader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



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


public class GestionProduitsController {

    private GestionProduits gestionProduits = new GestionProduits();

    @FXML
    private void handleAjouterProduit() {
        Viewloader.start("ajout_produit.fxml","ajouter_produit");
        }
    @FXML
    private void handleModifierProduit() {
        Viewloader.start("ModifierStock.fxml", "Modifier le stock");

    }


    @FXML
    private void handleSupprimerProduit() {

        Viewloader.start("SupprimerProduitView.fxml", "Supprimer un produit");
    }
    @FXML
    private void handleListerProduits() {
        Viewloader.start("AfficherProduitsView.fxml", "Afficher un produit");
    }


    @FXML
    private void handleAfficherParCategorie() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Catégorie");
        dialog.setHeaderText("Entrer la catégorie (ordinateur / telephone / accessoire) :");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(categorie -> gestionProduits.afficherProduitsParCategorie(categorie));


    }

    @FXML
    private void handleRetour() {
        // Code pour retourner au menu précédent
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }





}

