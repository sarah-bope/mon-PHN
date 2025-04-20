package org.example;

import org.example.dbManager.dbManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestionProduits {
    private List<Produit> produits;

    public GestionProduits() {
        this.produits = new ArrayList<>();
        chargerDepuisBDD();
    }

    public void afficherProduitsParCategorie(String categorie) {
        List<Produit> produitsFiltres = produits.stream()
                .filter(p -> {
                    if (categorie.equalsIgnoreCase("ordinateur")) return p instanceof Ordinateur;
                    if (categorie.equalsIgnoreCase("telephone")) return p instanceof Telephone;
                    if (categorie.equalsIgnoreCase("accessoire")) return p instanceof Accessoire;
                    return false;
                })
                .collect(Collectors.toList());

        if (produitsFiltres.isEmpty()) {
            System.out.println("\nAucun produit dans la catégorie '" + categorie + "'");
        } else {
            System.out.println("\nProduits " + categorie + " (" + produitsFiltres.size() + ")");
            produitsFiltres.forEach(System.out::println);
        }
    }

    public void afficherProduits() {
        // Recharger les produits depuis la base de données
        produits = chargerTousLesProduitsDepuisBDD();

        if (produits.isEmpty()) {
            System.out.println("\nAucun produit enregistré");
            return;
        }

        System.out.println("\nLISTE COMPLÈTE DES PRODUITS (" + produits.size() + ")");
        System.out.println("==========================================");

        for (int i = 0; i < produits.size(); i++) {
            System.out.println("Produit #" + (i+1));
            System.out.println("----------------------------------------");
            System.out.println(produits.get(i).toString());
            System.out.println("----------------------------------------\n");
        }

        System.out.println("==========================================");
        System.out.println("Total: " + produits.size() + " produits trouvés");
    }

    private List<Produit> chargerTousLesProduitsDepuisBDD() {
        List<Produit> produitsCharges = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM produits")) {

            while (rs.next()) {
                String categorie = rs.getString("categorie");
                Produit p = creerProduitDepuisResultSet(rs, categorie);
                if (p != null) {
                    produitsCharges.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement: " + e.getMessage());
        }

        return produitsCharges;
    }


    public boolean acheterProduit(int idProduit, int quantite) {
        Produit produit = trouverProduitParId(idProduit);
        if (produit == null) {
            System.out.println("Produit introuvable");
            return false;
        }

        if (quantite > produit.getQuantite()) {
            System.out.println("Stock insuffisant. Disponible: " + produit.getQuantite());
            return false;
        }

        produit.reduireStock(quantite);
        mettreAJourStockBDD(produit);
        System.out.println("Achat réussi! Nouveau stock: " + produit.getQuantite());
        return true;
    }

    public void modifierStock(Scanner scanner) {
        System.out.print("\nID du produit: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produit produit = trouverProduitParId(id);
        if (produit == null) {
            System.out.println("Produit introuvable");
            return;
        }

        System.out.println("Actuel: " + produit);
        System.out.print("Nouvelle quantité: ");
        int nouvelleQte = scanner.nextInt();
        scanner.nextLine();

        produit.setQuantite(nouvelleQte);
        mettreAJourStockBDD(produit);
        System.out.println("Stock mis à jour");
    }

    public void ajouterProduit(Scanner scanner) {
        System.out.println("\nAJOUT D'UN NOUVEAU PRODUIT");

        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nom: ");
        String nom = scanner.nextLine();

        System.out.print("Prix: ");
        double prix = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Quantité: ");
        int quantite = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Catégorie (ordinateur/telephone/accessoire): ");
        String categorie = scanner.nextLine().toLowerCase();

        Produit nouveauProduit = creerProduitSelonCategorie(id, nom, prix, quantite, categorie, scanner);
        if (nouveauProduit == null) return;

        nouveauProduit.ajouterProduit();
        produits.add(nouveauProduit);
        System.out.println("Produit ajouté");
    }

    public void supprimerProduit(Scanner scanner) {
        System.out.print("\nID du produit: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        produits.removeIf(p -> p.getId() == id);
        supprimerProduitDeBDD(id);
        System.out.println("Produit supprimé");
    }

    public void proposerAchat(Scanner scanner) {
        System.out.println("\nPROPOSITION D'ACHAT");
        System.out.print("Voulez-vous acheter un produit ? (oui/non): ");
        String reponse = scanner.nextLine().toLowerCase();

        if (reponse.equals("oui")) {
            afficherProduits();

            System.out.print("\nEntrez l'ID du produit à acheter: ");
            int idProduit = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Quantité à acheter: ");
            int quantite = scanner.nextInt();
            scanner.nextLine();

            boolean achatReussi = acheterProduit(idProduit, quantite);

            if (achatReussi) {
                System.out.println("✅ Achat confirmé ! Stock mis à jour dans la base de données.");
            } else {
                System.out.println("❌ Échec de l'achat. Vérifiez l'ID ou la quantité disponible.");
            }
        } else {
            System.out.println("Retour au menu principal.");
        }
    }

    private void chargerDepuisBDD() {
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM produits")) {

            while (rs.next()) {
                String categorie = rs.getString("categorie");
                Produit p = creerProduitDepuisResultSet(rs, categorie);
                if (p != null) produits.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur chargement: " + e.getMessage());
        }
    }

    private Produit creerProduitDepuisResultSet(ResultSet rs, String categorie) throws SQLException {
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        double prix = rs.getDouble("prix");
        int quantite = rs.getInt("quantite");

        switch (categorie) {
            case "ordinateur":
                return chargerDetailsOrdinateur(id, nom, prix, quantite);
            case "telephone":
                return chargerDetailsTelephone(id, nom, prix, quantite);
            case "accessoire":
                return chargerDetailsAccessoire(id, nom, prix, quantite);
            default:
                return null;
        }
    }

    private Ordinateur chargerDetailsOrdinateur(int id, String nom, double prix, int quantite) {
        String sql = "SELECT * FROM ordinateurs WHERE id_produit = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ordinateur(id, nom, prix, quantite,
                        rs.getString("processeur"),
                        rs.getInt("ram"),
                        rs.getDouble("taille_ecran"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur chargement ordinateur: " + e.getMessage());
        }
        return null;
    }

    private Telephone chargerDetailsTelephone(int id, String nom, double prix, int quantite) {
        String sql = "SELECT * FROM telephones WHERE id_produit = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Telephone(id, nom, prix, quantite,
                        rs.getString("marque"),
                        rs.getInt("memoire"),
                        rs.getInt("camera"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur chargement téléphone: " + e.getMessage());
        }
        return null;
    }

    private Accessoire chargerDetailsAccessoire(int id, String nom, double prix, int quantite) {
        String sql = "SELECT * FROM accessoires WHERE id_produit = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Accessoire(id, nom, prix, quantite,
                        rs.getString("compatibilite"),
                        rs.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur chargement accessoire: " + e.getMessage());
        }
        return null;
    }

    private Produit creerProduitSelonCategorie(int id, String nom, double prix, int quantite,
                                               String categorie, Scanner scanner) {
        switch (categorie) {
            case "ordinateur":
                System.out.print("Processeur: ");
                String processeur = scanner.nextLine();
                System.out.print("RAM (GB): ");
                int ram = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Taille écran (pouces): ");
                double taille = scanner.nextDouble();
                scanner.nextLine();
                return new Ordinateur(id, nom, prix, quantite, processeur, ram, taille);

            case "telephone":
                System.out.print("Marque: ");
                String marque = scanner.nextLine();
                System.out.print("Mémoire (GB): ");
                int memoire = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Caméra (MP): ");
                int camera = scanner.nextInt();
                scanner.nextLine();
                return new Telephone(id, nom, prix, quantite, marque, memoire, camera);

            case "accessoire":
                System.out.print("Compatibilité: ");
                String compatibilite = scanner.nextLine();
                System.out.print("Type: ");
                String type = scanner.nextLine();
                return new Accessoire(id, nom, prix, quantite, compatibilite, type);

            default:
                System.out.println("Catégorie invalide");
                return null;
        }
    }

    private void mettreAJourStockBDD(Produit produit) {
        String sql = "UPDATE produits SET quantite = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, produit.getQuantite());
            pstmt.setInt(2, produit.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour stock: " + e.getMessage());
        }
    }

    private void supprimerProduitDeBDD(int id) {
        String sql = "DELETE FROM produits WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur suppression produit: " + e.getMessage());
        }
    }

    private Produit trouverProduitParId(int id) {
        return produits.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Produit> getProduits() {
        return new ArrayList<>(produits);
    }
}