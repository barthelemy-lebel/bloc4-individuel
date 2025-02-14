package models;

import java.util.List;

public class Promotion {
    private int id;
    private String nom;
    private int annee;
    private List<Apprenant> apprenants; // Une promotion contient plusieurs apprenants (Relation 1, N)

    public Promotion(int id, String nom, int annee, List<Apprenant> apprenants) {
        this.id = id;
        this.nom = nom;
        this.annee = annee;
        this.apprenants = apprenants;
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

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public List<Apprenant> getApprenants() {
        return apprenants;
    }

    public void setApprenants(List<Apprenant> apprenants) {
        this.apprenants = apprenants;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                "nom='" + nom + '\'' +
                ", annee=" + annee +
                ", apprenants=" + apprenants +
                '}';
    }
}
