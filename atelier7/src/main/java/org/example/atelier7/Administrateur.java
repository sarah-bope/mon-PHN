package org.example.atelier7;

import java.util.Scanner;

public class Administrateur extends Utilisateur {

    public Administrateur(String nomUtilisateur, String motDePasse, String role) {
        super(nomUtilisateur, motDePasse, role); // Appel du constructeur de la classe parent (Utilisateur)
    }

    public void ajouterUtilisateur(Scanner scanner) {
        System.out.print("Nom d'utilisateur : ");
        String nomUtilisateur = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        System.out.print("Rôle (client, vendeur, admin) : ");
        String role = scanner.nextLine();

        // On crée un nouvel utilisateur avec les informations saisies
        Utilisateur utilisateur = new Utilisateur(nomUtilisateur, motDePasse, role);

        // Enregistrer l'utilisateur dans la base de données
        if (utilisateur.enregistrerUtilisateur()) {
            System.out.println("Utilisateur ajouté avec succès !");
        } else {
            System.out.println("Erreur lors de l'ajout de l'utilisateur.");
        }
    }
}
