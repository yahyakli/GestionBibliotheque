package models;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String categorie;
    private int quantite;

    public Livre() {
    }

    public Livre(int id, String titre, String auteur, String categorie, int quantite) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.quantite = quantite;
    }

    public Livre(String titre, String auteur, String categorie, int quantite) {
        this(0, titre, auteur, categorie, quantite);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return titre + " (" + auteur + ")";
    }
}
