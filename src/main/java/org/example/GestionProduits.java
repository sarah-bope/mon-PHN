package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionProduits {
    private List<Produit> produits;
    private static final String FICHIER_JSON = "produits.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GestionProduits() {
        this.produits = new ArrayList<>();
        chargerDepuisFichier();
    }

    public void afficherProduitsParCategorie(String categorie) {
        List<Produit> produitsFiltres = new ArrayList<>();

        for (Produit produit : produits) {
            if ((categorie.equals("ordinateur") && produit instanceof Ordinateur) ||
                    (categorie.equals("telephone") && produit instanceof Telephone) ||
                    (categorie.equals("accessoire") && produit instanceof Accessoire)) {
                produitsFiltres.add(produit);
            }
        }

        if (produitsFiltres.isEmpty()) {
            System.out.println("Aucun produit disponible dans cette catégorie.");
        } else {
            System.out.println("\nProduits disponibles dans la catégorie " + categorie + " :");
            for (Produit produit : produitsFiltres) {
                System.out.println(produit);
            }
        }
    }

    public boolean acheterProduit(int idProduit, int quantite) {
        Produit produitAchete = null;

        for (Produit produit : produits) {
            if (produit.getId() == idProduit) {
                produitAchete = produit;
                break;
            }
        }

        if (produitAchete == null) {
            System.out.println("❌ Produit introuvable. Vérifiez l'ID.");
            return false;
        }

        if (quantite <= produitAchete.getQuantite()) {
            produitAchete.reduireStock(quantite);
            sauvegarderDansFichier(); // mise à jour après achat
            System.out.println("✅ Achat réussi ! Nouveau stock : " + produitAchete.getQuantite());
            return true;
        } else {
            System.out.println("❌ Stock insuffisant ! Disponible : " + produitAchete.getQuantite());
            return false;
        }
    }

    public void modifierStock(Scanner scanner) {
        System.out.print("Entrez l'ID du produit dont vous voulez modifier le stock : ");
        int idModif = scanner.nextInt();
        scanner.nextLine();

        Produit produitTrouve = null;
        for (Produit produit : produits) {
            if (produit.getId() == idModif) {
                produitTrouve = produit;
                break;
            }
        }

        if (produitTrouve == null) {
            System.out.println("Aucun produit trouvé avec cet ID.");
            return;
        }

        System.out.print("Nouvelle quantité en stock : ");
        int nouvelleQuantite = scanner.nextInt();
        scanner.nextLine();

        produitTrouve.setQuantite(nouvelleQuantite);
        sauvegarderDansFichier(); // sauvegarde après modification
        System.out.println("✅ Stock mis à jour pour le produit " + produitTrouve.getNom() + ".");
    }

    public void ajouterProduit(Scanner scanner) {
        System.out.println("\nAjout d'un produit :");
        System.out.print("ID du produit : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nom du produit : ");
        String nom = scanner.nextLine();

        System.out.print("Prix du produit : ");
        double prix = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Quantité en stock : ");
        int quantite = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Catégorie (ordinateur, telephone, accessoire) : ");
        String categorie = scanner.nextLine().toLowerCase();

        Produit nouveauProduit;
        switch (categorie) {
            case "ordinateur":
                nouveauProduit = new Ordinateur(id, nom, prix, quantite);
                break;
            case "telephone":
                nouveauProduit = new Telephone(id, nom, prix, quantite);
                break;
            case "accessoire":
                nouveauProduit = new Accessoire(id, nom, prix, quantite);
                break;
            default:
                System.out.println("❌ Catégorie non valide. Produit non ajouté.");
                return;
        }

        produits.add(nouveauProduit);
        sauvegarderDansFichier(); // sauvegarde après ajout
        System.out.println("✅ Produit ajouté avec succès !");
    }

    public void supprimerProduit(Scanner scanner) {
        System.out.print("Entrez l'ID du produit à supprimer : ");
        int idSupp = scanner.nextInt();
        scanner.nextLine();

        boolean produitTrouvé = produits.removeIf(produit -> produit.getId() == idSupp);
        if (produitTrouvé) {
            sauvegarderDansFichier(); // sauvegarde après suppression
            System.out.println("✅ Produit supprimé avec succès.");
        } else {
            System.out.println("❌ Aucun produit trouvé avec cet ID.");
        }
    }

    public void afficherProduits() {
        if (produits.isEmpty()) {
            System.out.println("Aucun produit enregistré.");
        } else {
            System.out.println("\nListe des produits disponibles :");
            for (Produit produit : produits) {
                System.out.println(produit);
            }
        }
    }

    public void proposerAchat(Scanner scanner) {
        System.out.println("Voulez-vous acheter un produit ? (oui/non)");
        String reponse = scanner.nextLine().toLowerCase();

        if (reponse.equals("oui")) {
            System.out.print("Entrez l'ID du produit à acheter : ");
            int idProduit = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Quantité à acheter : ");
            int quantite = scanner.nextInt();
            scanner.nextLine();

            boolean achatReussi = acheterProduit(idProduit, quantite);

            if (achatReussi) {
                System.out.println("✅ Achat confirmé ! Stock mis à jour.");
            } else {
                System.out.println("❌ Échec de l'achat. Vérifiez l'ID ou la quantité disponible.");
            }
        } else {
            System.out.println("Retour au menu principal.");
        }
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    // 🔁 Méthode pour sauvegarder dans un fichier JSON
    private void sauvegarderDansFichier() {
        try (FileWriter writer = new FileWriter(FICHIER_JSON)) {
            gson.toJson(produits, writer);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // 🔁 Méthode pour charger depuis un fichier JSON
    private void chargerDepuisFichier() {
        try (FileReader reader = new FileReader(FICHIER_JSON)) {
            Type typeListe = new TypeToken<List<Produit>>() {}.getType();
            List<Produit> produitsCharges = gson.fromJson(reader, typeListe);
            if (produitsCharges != null) {
                this.produits = produitsCharges;
            }
        } catch (IOException e) {
            System.out.println("Aucun fichier de sauvegarde trouvé. Un nouveau sera créé.");
        }
    }
}
