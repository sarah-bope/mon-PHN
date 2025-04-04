import java.util.Scanner;

public class Client extends Utilisateur {
    public Client(String nomUtilisateur, String motDePasse) {
        super(nomUtilisateur, motDePasse);
    }

    public void afficherCategories() {
        System.out.println("\nBienvenue, acheteur ! Choisissez une catégorie :");
        System.out.println("1. Ordinateurs");
        System.out.println("2. Téléphones");
        System.out.println("3. Accessoires");
    }
}
