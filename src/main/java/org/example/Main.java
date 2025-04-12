package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

public class Main {
    private static final String PRODUITS_FILE = "produits.json";
    private static final String UTILISATEURS_FILE = "utilisateurs.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionProduits gestionProduits = new GestionProduits();
        Administrateur admin = new Administrateur();

        // Charger les données existantes
        chargerProduits(gestionProduits);
        chargerUtilisateurs(admin);

        // Authentification
        String role = admin.demarrerAuthentification(scanner);

        if (role.equals("client")) {
            Client client = new Client("client", "123");
            client.afficherCategories();
            gestionProduits.afficherProduits();
            gestionProduits.proposerAchat(scanner);
        } else if (role.equals("vendeur")) {
            boolean continuer = true;
            while (continuer) {
                System.out.println("\nMenu vendeur :");
                System.out.println("1. Afficher les produits");
                System.out.println("2. Ajouter un produit");
                System.out.println("3. Supprimer un produit");
                System.out.println("4. Modifier le stock");
                System.out.println("5. Afficher par catégorie");
                System.out.println("6. Sauvegarder");
                System.out.println("0. Quitter");

                System.out.print("Choix : ");
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        gestionProduits.afficherProduits();
                        break;
                    case 2:
                        gestionProduits.ajouterProduit(scanner);
                        break;
                    case 3:
                        gestionProduits.supprimerProduit(scanner);
                        break;
                    case 4:
                        gestionProduits.modifierStock(scanner);
                        break;
                    case 5:
                        System.out.print("Catégorie (ordinateur, telephone, accessoire) : ");
                        String cat = scanner.nextLine();
                        gestionProduits.afficherProduitsParCategorie(cat);
                        break;
                    case 6:
                        sauvegarderProduits(gestionProduits);
                        sauvegarderUtilisateurs(admin);
                        System.out.println("✅ Données sauvegardées !");
                        break;
                    case 0:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            }
        }

        // Sauvegarde automatique en fin de programme
        sauvegarderProduits(gestionProduits);
        sauvegarderUtilisateurs(admin);
        System.out.println("👋 Fin du programme.");
    }

    // -------- Sauvegarde et chargement JSON --------

    private static void sauvegarderProduits(GestionProduits gestionProduits) {
        try (FileWriter writer = new FileWriter(PRODUITS_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(gestionProduits.getProduits(), writer);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des produits : " + e.getMessage());
        }
    }

    private static void chargerProduits(GestionProduits gestionProduits) {
        try (FileReader reader = new FileReader(PRODUITS_FILE)) {
            Gson gson = new Gson();
            Type produitListType = new TypeToken<java.util.List<Produit>>(){}.getType();
            java.util.List<Produit> produits = gson.fromJson(reader, produitListType);
            gestionProduits.setProduits(produits != null ? produits : new java.util.ArrayList<>());
        } catch (IOException e) {
            System.out.println("Aucun fichier de produits trouvé. Liste vide.");
        }
    }

    private static void sauvegarderUtilisateurs(Administrateur admin) {
        try (FileWriter writer = new FileWriter(UTILISATEURS_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(admin.getUtilisateurs(), writer);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }
    }

    private static void chargerUtilisateurs(Administrateur admin) {
        try (FileReader reader = new FileReader(UTILISATEURS_FILE)) {
            Gson gson = new Gson();
            Type utilisateurListType = new TypeToken<java.util.List<Utilisateur>>(){}.getType();
            java.util.List<Utilisateur> utilisateurs = gson.fromJson(reader, utilisateurListType);
            admin.setUtilisateurs(utilisateurs != null ? utilisateurs : new java.util.ArrayList<>());
        } catch (IOException e) {
            System.out.println("Aucun fichier d'utilisateurs trouvé.");
        }
    }
}
