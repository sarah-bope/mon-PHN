package org.example;

import org.example.dbManager.dbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ordinateur extends Produit {
    private String processeur;
    private int RAM;
    private double tailleEcran;

    public Ordinateur(int id, String nom, double prix, int quantite, String processeur, int RAM, double tailleEcran) {
        super(id, nom, prix, quantite, "ordinateur");
        setProcesseur(processeur);
        setRAM(RAM);
        setTailleEcran(tailleEcran);
    }

    public String getProcesseur() { return processeur; }
    public int getRAM() { return RAM; }
    public double getTailleEcran() { return tailleEcran; }

    public void setProcesseur(String processeur) {
        if (processeur != null && !processeur.isEmpty()) {
            this.processeur = processeur;
        } else {
            throw new IllegalArgumentException("Le processeur ne peut pas être vide.");
        }
    }

    public void setRAM(int RAM) {
        if (RAM > 0) {
            this.RAM = RAM;
        } else {
            throw new IllegalArgumentException("La RAM doit être supérieure à 0.");
        }
    }

    public void setTailleEcran(double tailleEcran) {
        if (tailleEcran > 0) {
            this.tailleEcran = tailleEcran;
        } else {
            throw new IllegalArgumentException("La taille de l'écran doit être positive.");
        }
    }

    @Override
    public void ajouterProduit() {
        super.ajouterProduit();
        String sql = "INSERT INTO ordinateurs (id_produit, processeur, ram, taille_ecran) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        return super.toString() + String.format(", Processeur: %s, RAM: %dGo, Taille écran: %.1f pouces",
                processeur, RAM, tailleEcran);
    }
}