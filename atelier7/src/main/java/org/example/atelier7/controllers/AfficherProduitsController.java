package org.example.atelier7.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.example.atelier7.dbManager.dbManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AfficherProduitsController {

    @FXML
    private TextArea produitsArea;

    @FXML
    public void initialize() {
        try {
            Connection connection = dbManager.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM produits");

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append("\n");
                sb.append("Nom: ").append(rs.getString("nom")).append("\n");
                sb.append("Prix: ").append(rs.getDouble("prix")).append("\n");
                sb.append("Quantité: ").append(rs.getInt("quantite")).append("\n\n");
            }

            produitsArea.setText(sb.toString());

            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if (produitsArea != null) {
                produitsArea.setText("Erreur de chargement des produits.");
            }
        }
    }
}
