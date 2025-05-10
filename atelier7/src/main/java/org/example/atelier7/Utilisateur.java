package org.example.atelier7;

import org.example.atelier7.dbManager.dbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utilisateur {
    protected String nomUtilisateur;
    protected String motDePasse;
    protected String role;

    public Utilisateur(String nomUtilisateur, String motDePasse, String role) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role + " : " + nomUtilisateur;
    }

    // Méthode pour ajouter un utilisateur à la base de données
    public boolean enregistrerUtilisateur() {
        String sql = "INSERT INTO utilisateurs (nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomUtilisateur);
            pstmt.setString(2, nomUtilisateur + "@gmail.com");
            pstmt.setString(3, motDePasse);
            pstmt.setString(4, role);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
            return false;
        }
    }
}
