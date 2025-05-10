package org.example.atelier7;


public class Client extends Utilisateur {

    // Le rôle par défaut est "client"
    public Client(String nomUtilisateur, String motDePasse) {
        super(nomUtilisateur, motDePasse, "client");
    }

    public void afficherCategories() {
        System.out.println("\nBienvenue, acheteur ! Choisissez une catégorie :");
        System.out.println("1. Ordinateurs");
        System.out.println("2. Téléphones");
        System.out.println("3. Accessoires");
    }
}
