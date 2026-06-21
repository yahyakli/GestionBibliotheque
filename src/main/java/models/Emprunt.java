package models;

public class Emprunt {
    private int id;
    private int livreId;
    private int membreId;
    private String dateEmprunt;
    private String dateRetour;
    private String statut;

    public Emprunt() {
    }

    public Emprunt(int id, int livreId, int membreId, String dateEmprunt, String dateRetour, String statut) {
        this.id = id;
        this.livreId = livreId;
        this.membreId = membreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.statut = statut;
    }

    public Emprunt(int livreId, int membreId, String dateEmprunt, String statut) {
        this(0, livreId, membreId, dateEmprunt, null, statut);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }

    public int getMembreId() {
        return membreId;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }

    public String getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(String dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
