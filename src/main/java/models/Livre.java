package models;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String categorie;
    private String isbn;
    private String editeur;
    private String datePublication;
    private String emplacement;
    private int quantite;

    public Livre() {
    }

    public Livre(int id, String titre, String auteur, String categorie, String isbn, String editeur, String datePublication, String emplacement, int quantite) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.isbn = isbn;
        this.editeur = editeur;
        this.datePublication = datePublication;
        this.emplacement = emplacement;
        this.quantite = quantite;
    }

    public Livre(String titre, String auteur, String categorie, String isbn, String editeur, String datePublication, String emplacement, int quantite) {
        this(0, titre, auteur, categorie, isbn, editeur, datePublication, emplacement, quantite);
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
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
