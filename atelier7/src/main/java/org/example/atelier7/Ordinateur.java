package org.example.atelier7;

import org.example.atelier7.dbManager.dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ordinateur extends Produit {
    private String processeur;
    private int RAM;
    private double tailleEcran;

    public Ordinateur(int id, String nom, double prix, int quantite, String processeur, int RAM, double tailleEcran) {
        super(id, nom, prix, quantite, "ordinateur");
        this.processeur = processeur;
        this.RAM = RAM;
        this.tailleEcran = tailleEcran;
    }

    @Override
    public void ajouterProduit() {
        super.ajouterProduit();
        String sql = "INSERT INTO ordinateurs (id_produit, processeur, ram, taille_ecran) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, processeur);
            pstmt.setInt(3, RAM);
            pstmt.setDouble(4, tailleEcran);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'ordinateur : " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Processeur: %s, RAM: %dGo, Taille écran: %.1f pouces", processeur, RAM, tailleEcran);
    }
}
