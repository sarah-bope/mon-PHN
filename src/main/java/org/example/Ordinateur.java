package org.example;

public class Ordinateur extends Produit {
    private String processeur;
    private int RAM;
    private double tailleEcran;

    public Ordinateur(int id, String nom, double prix, int quantite, String processeur, int RAM, double tailleEcran) {
        super(id, nom, prix, quantite);
        this.processeur = processeur;
        this.RAM = RAM;
        this.tailleEcran = tailleEcran;
    }

    public Ordinateur(int id, String nom, double prix, int quantite) {
    }

    public String getProcesseur() { return processeur; }
    public int getRAM() { return RAM; }
    public double getTailleEcran() { return tailleEcran; }

    public void setProcesseur(String processeur) {
        if (processeur != null && !processeur.isEmpty()) {
            this.processeur = processeur;
        } else {
            System.out.println("Le processeur ne peut pas être vide.");
        }
    }

    public void setRAM(int RAM) {
        if (RAM > 0) {
            this.RAM = RAM;
        } else {
            System.out.println("La RAM doit être supérieure à 0.");
        }
    }

    public void setTailleEcran(double tailleEcran) {
        if (tailleEcran > 0) {
            this.tailleEcran = tailleEcran;
        } else {
            System.out.println("La taille de l'écran doit être positive.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Processeur: %s, RAM: %dGo, Taille écran: %.1f pouces",
                processeur, RAM, tailleEcran);
    }
}
