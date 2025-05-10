package org.example.atelier7.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.atelier7.GestionProduits;
import org.example.atelier7.Produit;
import org.example.atelier7.dbManager.dbManager;
import org.example.atelier7.service.Viewloader;



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

public class ProduitController {
    private final GestionProduits gestionProduits;
    private final ObservableList<Produit> produitsObservable;

    public ProduitController() {
        this.gestionProduits = new GestionProduits();
        this.produitsObservable = FXCollections.observableArrayList(gestionProduits.getProduits());
    }

    public ObservableList<Produit> getProduitsObservable() {
        return produitsObservable;
    }

    public void supprimerProduit(int id) {
        boolean removed = produitsObservable.removeIf(p -> p.getId() == id);
        if (removed) {
            gestionProduits.supprimerProduitParId(id); // Cette méthode doit exister dans GestionProduits
            System.out.println("Produit supprimé");
        } else {
            System.out.println("Produit non trouvé");
        }
    }
}
