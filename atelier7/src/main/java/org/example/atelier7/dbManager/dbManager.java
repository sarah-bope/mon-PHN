package org.example.atelier7.dbManager;

import java.sql.Connection;
import java.sql.DriverManager;
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

    // Méthode d'initialisation des tables
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

            // Création des tables pour chaque sous-genre de produit
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
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création des tables : " + e.getMessage());
        }
    }
}
