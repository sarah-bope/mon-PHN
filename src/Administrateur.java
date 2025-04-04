import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrateur {
    private List<Utilisateur> utilisateurs;

    public Administrateur() {
        this.utilisateurs = new ArrayList<>();
    }

    public String demarrerAuthentification(Scanner scanner) {
        System.out.print("Êtes-vous un vendeur ou un client ? ");
        String role = scanner.nextLine().toLowerCase();

        if (role.equals("vendeur") || role.equals("client")) {
            return connexionOuInscription(scanner, role);
        } else {
            System.out.println("Rôle non valide.");
            return "";
        }
    }

    private String connexionOuInscription(Scanner scanner, String role) {
        System.out.print("Nom d'utilisateur : ");
        String nomUtilisateur = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        Utilisateur utilisateurExistant = rechercherUtilisateur(nomUtilisateur);

        if (utilisateurExistant != null) {
            System.out.println("Connexion réussie ! Bienvenue, " + nomUtilisateur);
            return role;
        } else {
            System.out.println("Utilisateur non trouvé. Voulez-vous vous inscrire ? (oui/non)");
            String reponse = scanner.nextLine().toLowerCase();
            if (reponse.equals("oui")) {
                utilisateurs.add(new Utilisateur(nomUtilisateur, motDePasse));
                System.out.println("Inscription réussie !");
                return role;
            } else {
                System.out.println("Connexion annulée.");
                return "";
            }
        }
    }

    private Utilisateur rechercherUtilisateur(String nomUtilisateur) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getNomUtilisateur().equals(nomUtilisateur)) {
                return utilisateur;
            }
        }
        return null;
    }
}
