package models;

public class Categorie {
    private int id;
    private String nom;
    private int ageMin;
    private Integer ageMax;  // Integer nullable

    // Constructeur par défaut
    public Categorie() {}

    // Constructeur avec paramètres
    public Categorie(int id, String nom, int ageMin, Integer ageMax) {
        this.id = id;
        this.nom = nom;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
    }

    // Getters et Setters
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

    public int getAgeMin() {
        return ageMin;
    }
    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }
    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }
}
