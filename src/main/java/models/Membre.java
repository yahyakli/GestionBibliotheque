package models;

public class Membre {
    private int id;
    private String nom;
    private String email;
    private String telephone;
    private String dateInscription;

    public Membre() {
    }

    public Membre(int id, String nom, String email, String telephone, String dateInscription) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.dateInscription = dateInscription;
    }

    public Membre(String nom, String email, String telephone, String dateInscription) {
        this(0, nom, email, telephone, dateInscription);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    @Override
    public String toString() {
        return nom;
    }
}
