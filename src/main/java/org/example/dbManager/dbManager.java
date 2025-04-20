package org.example.dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbManager {
    private static final String url = "jdbc:sqlite:Gstock.db";
    private static Connection connection;

    // Méthode de connexion à la base de données
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    // Méthode d'initialisation des tables et de l'admin
    public static void initialiser() {
        try (Connection conn = getConnection(); Statement smt = conn.createStatement()) {
            // Création de la table utilisateurs
            String sqlUtilisateurs = """
                CREATE TABLE IF NOT EXISTS utilisateurs (
                    id INTEGER PRIMARY KEY,
                    nom TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    mot_de_passe TEXT NOT NULL,
                    role TEXT CHECK(role IN ('client', 'vendeur', 'admin')) NOT NULL
                );
            """;
            smt.execute(sqlUtilisateurs);

            // Création de la table produits
            String sqlProduits = """
                CREATE TABLE IF NOT EXISTS produits (
                    id INTEGER PRIMARY KEY,
                    nom TEXT NOT NULL,
                    prix REAL NOT NULL,
                    quantite INTEGER NOT NULL,
                    categorie TEXT,
                    details TEXT,
                    id_vendeur INTEGER,
                    FOREIGN KEY (id_vendeur) REFERENCES utilisateurs(id)
                );
            """;
            smt.execute(sqlProduits);

            // Tables pour chaque type de produit
            String sqlOrdinateur = """
                CREATE TABLE IF NOT EXISTS ordinateurs (
                    id INTEGER PRIMARY KEY,
                    id_produit INTEGER,
                    processeur TEXT,
                    ram INTEGER,
                    taille_ecran REAL,
                    FOREIGN KEY (id_produit) REFERENCES produits(id)
                );
            """;
            smt.execute(sqlOrdinateur);

            String sqlTelephone = """
                CREATE TABLE IF NOT EXISTS telephones (
                    id INTEGER PRIMARY KEY,
                    id_produit INTEGER,
                    marque TEXT,
                    memoire INTEGER,
                    camera INTEGER,
                    FOREIGN KEY (id_produit) REFERENCES produits(id)
                );
            """;
            smt.execute(sqlTelephone);

            String sqlAccessoire = """
                CREATE TABLE IF NOT EXISTS accessoires (
                    id INTEGER PRIMARY KEY,
                    id_produit INTEGER,
                    compatibilite TEXT,
                    type TEXT,
                    FOREIGN KEY (id_produit) REFERENCES produits(id)
                );
            """;
            smt.execute(sqlAccessoire);

            // Initialisation du compte admin
            initialiserAdmin(conn);

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'initialisation : " + e.getMessage());
        }
    }

    // Méthode pour initialiser le compte admin
    private static void initialiserAdmin(Connection conn) throws SQLException {
        // Vérifier si l'admin existe déjà
        String checkSql = "SELECT 1 FROM utilisateurs WHERE role = 'admin' LIMIT 1";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Un administrateur existe déjà dans la base.");
                return;
            }
        }


        String insertSql = "INSERT INTO utilisateurs (nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, "AdminPrincipal");
            pstmt.setString(2, "admin@system.com");
            pstmt.setString(3, "admin123");
            pstmt.setString(4, "admin");

            pstmt.executeUpdate();
            System.out.println("Compte administrateur créé avec succès.");
        }
    }
}