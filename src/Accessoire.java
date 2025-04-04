public class Accessoire extends Produit {
    private String compatibilite;
    private String type;

    public Accessoire(int id, String nom, double prix, int quantite, String compatibilite, String type) {
        super(id, nom, prix, quantite);
        this.compatibilite = compatibilite;
        this.type = type;
    }

    public Accessoire(int id, String nom, double prix, int quantite) {
    }

    public String getCompatibilite() { return compatibilite; }
    public String getType() { return type; }

    public void setCompatibilite(String compatibilite) {
        if (compatibilite != null && !compatibilite.isEmpty()) {
            this.compatibilite = compatibilite;
        } else {
            System.out.println("La compatibilité ne peut pas être vide.");
        }
    }

    public void setType(String type) {
        if (type != null && !type.isEmpty()) {
            this.type = type;
        } else {
            System.out.println("Le type ne peut pas être vide.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Compatibilité: %s, Type: %s", compatibilite, type);
    }
}
