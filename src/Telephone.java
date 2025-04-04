public class Telephone extends Produit {
    private String marque;
    private int memoire;
    private int camera;

    public Telephone(int id, String nom, double prix, int quantite, String marque, int memoire, int camera) {
        super(id, nom, prix, quantite);
        this.marque = marque;
        this.memoire = memoire;
        this.camera = camera;
    }

    public Telephone(int id, String nom, double prix, int quantite) {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Marque: %s, Mémoire: %dGo, Caméra: %dMP", marque, memoire, camera);
    }
}
