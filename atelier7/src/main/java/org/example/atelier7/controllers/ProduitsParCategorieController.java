package org.example.atelier7.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.atelier7.GestionProduits;
import org.example.atelier7.Produit;
import java.util.List;


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


public class ProduitsParCategorieController {

    @FXML private ComboBox<String> categorieComboBox;
    @FXML private TableView<Produit> produitsTable;
    @FXML private TableColumn<Produit, Integer> idCol;
    @FXML private TableColumn<Produit, String> nomCol;
    @FXML private TableColumn<Produit, Double> prixCol;
    @FXML private TableColumn<Produit, Integer> quantiteCol;
    @FXML private TableColumn<Produit, String> categorieCol;

    private final GestionProduits gestionProduits = new GestionProduits();

    @FXML
    public void initialize() {
        categorieComboBox.setItems(FXCollections.observableArrayList("ordinateur", "telephone", "accessoire"));

        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        nomCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        prixCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrix()).asObject());
        quantiteCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getQuantite()).asObject());
        categorieCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategorie()));
    }

    @FXML
    private void handleFiltrer() {
        String categorie = categorieComboBox.getValue();
        if (categorie != null) {
            List<Produit> filtres = gestionProduits.getProduitsParCategorie(categorie);
            ObservableList<Produit> observableList = FXCollections.observableArrayList(filtres);
            produitsTable.setItems(observableList);
        }
    }
}
