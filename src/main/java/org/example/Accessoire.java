package org.example;

import org.example.dbManager.dbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Accessoire extends Produit {
    private String compatibilite;
    private String type;

    // Constructeur principal
    public Accessoire(int id, String nom, double prix, int quantite, String compatibilite, String type) {
        super(id, nom, prix, quantite, "accessoire");
        setCompatibilite(compatibilite);
        setType(type);
    }

    // Constructeur simplifié (optionnel)
    public Accessoire(int id, String nom, double prix, int quantite) {
        super(id, nom, prix, quantite, "accessoire");
    }

    // Getters
    public String getCompatibilite() { return compatibilite; }
    public String getType() { return type; }

    // Setters avec validation
    public void setCompatibilite(String compatibilite) {
        if (compatibilite != null && !compatibilite.isEmpty()) {
            this.compatibilite = compatibilite;
        } else {
            throw new IllegalArgumentException("La compatibilité ne peut pas être vide.");
        }
    }

    public void setType(String type) {
        if (type != null && !type.isEmpty()) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Le type ne peut pas être vide.");
        }
    }

    // Méthode d'ajout en base de données
    @Override
    public void ajouterProduit() {
        // Validation des attributs avant insertion
        if (compatibilite == null || type == null) {
            throw new IllegalStateException("Impossible d'ajouter un accessoire avec des champs non initialisés");
        }

        super.ajouterProduit(); // Ajoute d'abord dans la table produits

        String sql = "INSERT INTO accessoires (id_produit, compatibilite, type) VALUES (?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, compatibilite);
            pstmt.setString(3, type);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'accessoire : " + e.getMessage());
            // On pourrait rollback ici si nécessaire
        }
    }

    // Méthode de mise à jour en base de données
    public void mettreAJourAccessoire() {
        String sql = "UPDATE accessoires SET compatibilite = ?, type = ? WHERE id_produit = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, compatibilite);
            pstmt.setString(2, type);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'accessoire : " + e.getMessage());
        }
    }

    // Représentation textuelle
    @Override
    public String toString() {
        return super.toString() + String.format(", Compatibilité: %s, Type: %s",
                compatibilite, type);
    }

    // Méthode pour charger un accessoire depuis la base de données
    public static Accessoire chargerDepuisBDD(int idProduit) {
        String sql = "SELECT p.*, a.compatibilite, a.type FROM produits p " +
                "JOIN accessoires a ON p.id = a.id_produit WHERE p.id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idProduit);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Accessoire(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("prix"),
                        rs.getInt("quantite"),
                        rs.getString("compatibilite"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement de l'accessoire : " + e.getMessage());
        }
        return null;
    }
}