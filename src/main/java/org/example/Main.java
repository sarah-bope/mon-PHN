package org.example;

import org.example.dbManager.dbManager;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GestionProduits gestionProduits = new GestionProduits();
    private static final Administrateur admin = new Administrateur("admin", "admin123", "admin");

    public static void main(String[] args) {
        // Initialisation de la base de données
        dbManager.initialiser();

        while (true) {
            System.out.println("\n=== SYSTÈME DE GESTION ===");
            System.out.println("1. Connexion Administrateur");
            System.out.println("2. Connexion Vendeur");
            System.out.println("3. Connexion Client");
            System.out.println("4. Inscription Client");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le retour à la ligne

            switch (choix) {
                case 1 -> authentifierAdmin();
                case 2 -> authentifierVendeur();
                case 3 -> authentifierClient();
                case 4 -> inscrireClient();
                case 0 -> {
                    System.out.println("✅ Déconnexion réussie. Au revoir !");
                    System.exit(0);
                }
                default -> System.out.println("❌ Choix invalide.");
            }
        }
    }

    private static void authentifierAdmin() {
        System.out.println("\n=== CONNEXION ADMIN ===");
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();

        if (username.equals("AdminPrincipal") && password.equals("admin123")) {
            menuAdmin();
        } else {
            System.out.println("❌ Identifiants incorrects.");
        }
    }

    private static void authentifierVendeur() {
        System.out.println("\n=== CONNEXION VENDEUR ===");
        String role = admin.demarrerAuthentification(scanner);
        if ("vendeur".equals(role)) {
            menuVendeur();
        }
    }

    private static void authentifierClient() {
        System.out.println("\n=== CONNEXION CLIENT ===");
        String role = admin.demarrerAuthentification(scanner);
        if ("client".equals(role)) {
            menuClient();
        }
    }

    private static void inscrireClient() {
        System.out.println("\n=== INSCRIPTION CLIENT ===");
        System.out.print("Choisissez un nom d'utilisateur : ");
        String username = scanner.nextLine();

        System.out.print("Choisissez un mot de passe : ");
        String password = scanner.nextLine();

        Client nouveauClient = new Client(username, password);
        if (nouveauClient.enregistrerUtilisateur()) {
            System.out.println("✅ Inscription réussie !");
        } else {
            System.out.println("❌ Erreur lors de l'inscription.");
        }
    }

    private static void menuAdmin() {
        while (true) {
            System.out.println("\n=== MENU ADMINISTRATEUR ===");
            System.out.println("1. Gérer les utilisateurs");
            System.out.println("2. Gérer les produits");
            System.out.println("0. Déconnexion");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> gererUtilisateurs();
                case 2 -> gererProduits();
                case 0 -> {
                    System.out.println("✅ Déconnexion admin réussie.");
                    return;
                }
                default -> System.out.println("❌ Choix invalide.");
            }
        }
    }

    private static void menuVendeur() {
        while (true) {
            System.out.println("\n=== MENU VENDEUR ===");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Modifier le stock");
            System.out.println("3. Afficher tous les produits");
            System.out.println("4. Supprimer un produit");
            System.out.println("0. Déconnexion");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> gestionProduits.ajouterProduit(scanner);
                case 2 -> gestionProduits.modifierStock(scanner);
                case 3 -> gestionProduits.afficherProduits();
                case 4 -> gestionProduits.supprimerProduit(scanner);
                case 0 -> {
                    System.out.println("✅ Déconnexion vendeur réussie.");
                    return;
                }
                default -> System.out.println("❌ Choix invalide.");
            }
        }
    }

    private static void menuClient() {
        while (true) {
            System.out.println("\n=== MENU CLIENT ===");
            System.out.println("1. Afficher par catégorie");
            System.out.println("2. Afficher tous les produits");
            System.out.println("3. Acheter un produit");
            System.out.println("0. Déconnexion");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.print("Catégorie (ordinateur/telephone/accessoire) : ");
                    String categorie = scanner.nextLine();
                    gestionProduits.afficherProduitsParCategorie(categorie);
                }
                case 2 -> gestionProduits.afficherProduits();
                case 3 -> gestionProduits.proposerAchat(scanner);
                case 0 -> {
                    System.out.println("✅ Déconnexion clie // Méthode pour ajouter un utilisateurnt réussie.");
                    return;
                }
                default -> System.out.println("❌ Choix invalide.");
            }
        }
    }

    private static void gererUtilisateurs() {
        while (true) {
            System.out.println("\n=== GESTION UTILISATEURS ===");
            System.out.println("1. Ajouter un utilisateur");
            System.out.println("2. Lister les utilisateurs");
            System.out.println("3. Supprimer un utilisateur");
            System.out.println("0. Retour");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> admin.ajouterUtilisateur(scanner);
                case 2 -> admin.listerUtilisateurs();
                case 3 -> admin.supprimerUtilisateur(scanner);
                case 0 -> {
                    return;
                }
                default -> System.out.println("❌ Choix invalide.");
            }
        }
    }

    private static void gererProduits() {
        while (true) {
            System.out.println("\n=== GESTION PRODUITS ===");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Lister tous les produits");
            System.out.println("3. Modifier un produit");
            System.out.println("4. Supprimer un produit");
            System.out.println("5. Afficher par catégorie");
            System.out.println("0. Retour");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> gestionProduits.ajouterProduit(scanner);
                case 2 -> gestionProduits.afficherProduits();
                case 3 -> gestionProduits.modifierStock(scanner);
                case 4 -> gestionProduits.supprimerProduit(scanner);
                case 5 -> {
                    System.out.print("Catégorie (ordinateur/telephone/accessoire) : ");
                    String categorie = scanner.nextLine();
                    gestionProduits.afficherProduitsParCategorie(categorie);
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("❌ Choix invalide.");
            }
        }
    }
}