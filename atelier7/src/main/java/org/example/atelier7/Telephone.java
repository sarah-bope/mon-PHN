package org.example.atelier7;

import org.example.atelier7.dbManager.dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Telephone extends Produit {
    private String marque;
    private int memoire;
    private int camera;

    public Telephone(int id, String nom, double prix, int quantite, String marque, int memoire, int camera) {
        super(id, nom, prix, quantite, "telephone");
        this.marque = marque;
        this.memoire = memoire;
        this.camera = camera;
    }

    @Override
    public void ajouterProduit() {
        super.ajouterProduit();
        String sql = "INSERT INTO telephones (id_produit, marque, memoire, camera) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, marque);
            pstmt.setInt(3, memoire);
            pstmt.setInt(4, camera);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du téléphone : " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Marque: %s, Mémoire: %dGo, Caméra: %dMP", marque, memoire, camera);
    }
}
