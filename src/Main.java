import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionProduits gestionProduits = new GestionProduits();

        while (true) {
            System.out.println("\n=== Bienvenue ===");
            System.out.println("1. Vendeur");
            System.out.println("2. Client");
            System.out.println("3. Quitter");
            System.out.print("Choisissez votre rôle : ");

            int choixRole = scanner.nextInt();
            scanner.nextLine();

            if (choixRole == 1) {
                afficherMenuVendeur(scanner, gestionProduits);
            } else if (choixRole == 2) {
                afficherMenuClient(scanner, gestionProduits);
            } else if (choixRole == 3) {
                System.out.println("✅ Déconnexion réussie. Au revoir !");
                break;
            } else {
                System.out.println("❌ Option invalide. Essayez encore.");
            }
        }

        scanner.close();
    }

    private static void afficherMenuVendeur(Scanner scanner, GestionProduits gestionProduits) {
        while (true) {
            System.out.println("\n=== Menu Vendeur ===");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Modifier le stock d'un produit");
            System.out.println("3. Afficher tous les produits");
            System.out.println("4. Supprimer un produit");
            System.out.println("5. Quitter");
            System.out.println("6. Retour à la connexion");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> gestionProduits.ajouterProduit(scanner);
                case 2 -> gestionProduits.modifierStock(scanner);
                case 3 -> gestionProduits.afficherProduits();
                case 4 -> gestionProduits.supprimerProduit(scanner);
                case 5 -> {
                    System.out.println("✅ Déconnexion du menu vendeur.");
                    return;
                }
                case 6 -> {
                    System.out.println("🔄 Retour à la connexion...");
                    return;
                }
                default -> System.out.println("❌ Option invalide. Essayez encore.");
            }
        }
    }

    private static void afficherMenuClient(Scanner scanner, GestionProduits gestionProduits) {
        while (true) {
            System.out.println("\n=== Menu Client ===");
            System.out.println("1. Choisir une catégorie");
            System.out.println("2. Acheter un produit");
            System.out.println("3. Quitter");
            System.out.println("4. Retour à la connexion");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> {
                    System.out.println("Choisissez une catégorie :");
                    System.out.println("1. Ordinateurs");
                    System.out.println("2. Téléphones");
                    System.out.println("3. Accessoires");
                    System.out.print("Votre choix : ");
                    int categorieChoix = scanner.nextInt();
                    scanner.nextLine();

                    String categorie = switch (categorieChoix) {
                        case 1 -> "ordinateur";
                        case 2 -> "telephone";
                        case 3 -> "accessoire";
                        default -> {
                            System.out.println("❌ Option non valide.");
                            yield "";
                        }
                    };

                    if (!categorie.isEmpty()) {
                        gestionProduits.afficherProduitsParCategorie(categorie);
                    }
                }
                case 2 -> gestionProduits.proposerAchat(scanner);
                case 3 -> {
                    System.out.println("✅ Déconnexion du menu client.");
                    return;
                }
                case 4 -> {
                    System.out.println("🔄 Retour à la connexion...");
                    return;
                }
                default -> System.out.println("❌ Option invalide. Essayez encore.");
            }
        }
    }
}
