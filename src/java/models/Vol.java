package models;

import java.time.LocalDateTime;

public class Vol {
    private int id;
    private String nom;
    private Avion avion;
    private VilleDesservie villeDepart;
    private VilleDesservie villeArriver;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArriver;
   
    public Vol(){}
    // Constructeur 
    public Vol(int id, String nom, Avion avion, VilleDesservie villeDepart, VilleDesservie villeArriver, LocalDateTime dateDepart, LocalDateTime dateArriver) {
        this.id = id;
        this.nom = nom;
        this.avion = avion;
        this.villeDepart = villeDepart;
        this.villeArriver = villeArriver;
        this.dateDepart = dateDepart;
        this.dateArriver = dateArriver;
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

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public VilleDesservie getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(VilleDesservie villeDepart) {
        this.villeDepart = villeDepart;
    }

    public VilleDesservie getVilleArriver() {
        return villeArriver;
    }

    public void setVilleArriver(VilleDesservie villeArriver) {
        this.villeArriver = villeArriver;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalDateTime getDateArriver() {
        return dateArriver;
    }

    public void setDateArriver(LocalDateTime dateArriver) {
        this.dateArriver = dateArriver;
    }

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "Vol{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", avion=" + avion +
                ", villeDepart=" + villeDepart +
                ", villeArriver=" + villeArriver +
                ", dateDepart=" + dateDepart +
                ", dateArriver=" + dateArriver +
                '}';
    }
}
