package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class SauvegardeJson {

    private static final Gson gson = new Gson();

    // Sauvegarde des produits dans un fichier JSON
    public static void sauvegarderProduits(List<Produit> produits, String fichier) {
        try (FileWriter writer = new FileWriter(fichier)) {
            gson.toJson(produits, writer);
            System.out.println("✅ Produits sauvegardés avec succès !");
        } catch (IOException e) {
            System.out.println("❌ Erreur lors de la sauvegarde des produits : " + e.getMessage());
        }
    }

    // Chargement des produits depuis un fichier JSON
    public static List<Produit> chargerProduits(String fichier) {
        try (FileReader reader = new FileReader(fichier)) {
            Type produitListType = new TypeToken<List<Produit>>(){}.getType();
            return gson.fromJson(reader, produitListType);
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du chargement des produits : " + e.getMessage());
            return null;
        }
    }

    // Sauvegarde des utilisateurs
    public static void sauvegarderUtilisateurs(List<Utilisateur> utilisateurs, String fichier) {
        try (FileWriter writer = new FileWriter(fichier)) {
            gson.toJson(utilisateurs, writer);
            System.out.println("✅ Utilisateurs sauvegardés avec succès !");
        } catch (IOException e) {
            System.out.println("❌ Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
        }
    }

    // Chargement des utilisateurs
    public static List<Utilisateur> chargerUtilisateurs(String fichier) {
        try (FileReader reader = new FileReader(fichier)) {
            Type userListType = new TypeToken<List<Utilisateur>>(){}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du chargement des utilisateurs : " + e.getMessage());
            return null;
        }
    }
}
