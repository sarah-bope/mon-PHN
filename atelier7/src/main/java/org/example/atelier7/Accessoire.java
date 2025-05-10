package org.example.atelier7;

import org.example.atelier7.dbManager.dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Accessoire extends Produit {
    private String compatibilite;
    private String type;

    public Accessoire(int id, String nom, double prix, int quantite, String compatibilite, String type) {
        super(id, nom, prix, quantite, "accessoire");
        this.compatibilite = compatibilite;
        this.type = type;
    }

    @Override
    public void ajouterProduit() {
        super.ajouterProduit();
        String sql = "INSERT INTO accessoires (id_produit, compatibilite, type) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, compatibilite);
            pstmt.setString(3, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'accessoire : " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Compatibilité: %s, Type: %s", compatibilite, type);
    }
}
