package org.example.atelier7;

import org.example.atelier7.dbManager.dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Produit {
    protected int id;
    protected String nom;
    protected double prix;
    protected int quantite;
    protected String categorie;

    public Produit(int id, String nom, double prix, int quantite, String categorie) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    public void ajouterProduit() {
        String sql = "INSERT INTO produits (id, nom, prix, quantite, categorie) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nom);
            pstmt.setDouble(3, prix);
            pstmt.setInt(4, quantite);
            pstmt.setString(5, categorie);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du produit : " + e.getMessage());
        }
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getQuantite() { return quantite; }
    public String getCategorie() { return categorie; }

    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void reduireStock(int quantite) { this.quantite -= quantite; }

    @Override
    public String toString() {
        return String.format("ID: %d, Nom: %s, Prix: %.2f€, Quantité: %d, Catégorie: %s",
                id, nom, prix, quantite, categorie);
    }
}
