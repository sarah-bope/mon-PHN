package org.example;

import org.example.dbManager.dbManager;
import java.sql.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Administrateur extends Utilisateur {

    public Administrateur(String nom, String motDePasse, String role) {
        super(nom, motDePasse, role);
    }

    public void ajouterUtilisateur(Scanner scanner) {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        String email = nom + "@gmail.com";

        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        System.out.print("Rôle (client/vendeur/admin) : ");
        String role = scanner.nextLine();

        if (enregistrerUtilisateurDB(nom, email, motDePasse, role)) {
            System.out.println("Utilisateur ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout de l'utilisateur.");
        }
    }


    public String demarrerAuthentification(Scanner scanner) {
        System.out.print("Êtes-vous un vendeur ou un client ? ");
        String role = scanner.nextLine().toLowerCase();

        if (role.equals("vendeur") || role.equals("client")) {
            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.print("Mot de passe : ");
            String motDePasse = scanner.nextLine();

            if (verifierUtilisateurDB(nom, motDePasse)) {
                System.out.println("Connexion réussie ! Bienvenue, " + nom);
                return role;
            } else {
                System.out.println("Utilisateur non trouvé. Voulez-vous vous inscrire ? (oui/non)");
                String reponse = scanner.nextLine().toLowerCase();
                if (reponse.equals("oui")) {
                    String email = nom + "@gmail.com";
                    if (enregistrerUtilisateurDB(nom, email, motDePasse, role)) {
                        System.out.println("Inscription réussie !");
                        return role;
                    } else {
                        System.out.println("Échec de l'inscription.");
                        return "";
                    }
                } else {
                    System.out.println("Connexion annulée.");
                    return "";
                }
            }
        } else {
            System.out.println("Rôle non valide.");
            return "";
        }
    }


    private boolean enregistrerUtilisateurDB(String nom, String email, String motDePasse, String role) {
        String sql = "INSERT INTO utilisateurs (nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, email);
            pstmt.setString(3, motDePasse);
            pstmt.setString(4, role);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur DB: " + e.getMessage());
            return false;
        }
    }


    private boolean verifierUtilisateurDB(String nom, String motDePasse) {
        String sql = "SELECT * FROM utilisateurs WHERE nom = ? AND mot_de_passe = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, motDePasse);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Erreur DB: " + e.getMessage());
            return false;
        }
    }


    public void listerUtilisateurs() {
        String sql = "SELECT id, nom, role FROM utilisateurs";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nLISTE DES UTILISATEURS");
            System.out.println("----------------------");
            System.out.printf("%-5s | %-20s | %-10s%n", "ID", "Nom", "Rôle");
            System.out.println("------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d | %-20s | %-10s%n",
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("role"));
            }

        } catch (SQLException e) {
            System.out.println("Erreur DB: " + e.getMessage());
        }
    }


    public void supprimerUtilisateur(Scanner scanner) {
        System.out.print("ID de l'utilisateur à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM utilisateurs WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur DB: " + e.getMessage());
        }
    }
}