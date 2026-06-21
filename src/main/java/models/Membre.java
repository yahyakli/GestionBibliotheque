package models;

public class Membre {
    private int id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private String dateNaissance;
    private String dateInscription;
    private String typeAdherent;
    private String statut;

    public Membre() {
    }

    public Membre(int id, String nom, String email, String telephone, String adresse, String dateNaissance, String dateInscription, String typeAdherent, String statut) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.dateInscription = dateInscription;
        this.typeAdherent = typeAdherent;
        this.statut = statut;
    }

    public Membre(String nom, String email, String telephone, String adresse, String dateNaissance, String dateInscription, String typeAdherent, String statut) {
        this(0, nom, email, telephone, adresse, dateNaissance, dateInscription, typeAdherent, statut);
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getTypeAdherent() {
        return typeAdherent;
    }

    public void setTypeAdherent(String typeAdherent) {
        this.typeAdherent = typeAdherent;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return nom;
    }
}
