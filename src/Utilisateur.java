public class Utilisateur {
    protected String nomUtilisateur;
    protected String motDePasse;

    public Utilisateur(String nomUtilisateur, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
    }

    public String getNomUtilisateur() { return nomUtilisateur; }
    public String getMotDePasse() { return motDePasse; }

    @Override
    public String toString() {
        return "Utilisateur: " + nomUtilisateur;
    }
}
