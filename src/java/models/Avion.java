package models;

import java.time.LocalDateTime;

public class Avion {
    private int id;
    private String nom;
    private Modele modele;
    private LocalDateTime dateFabrication;

    public Avion(){}
 public Avion(int id){this.id=id;}
    public Avion(int id,String nom, Modele modele , LocalDateTime dateFabrication){
         this.id = id;
         this.nom = nom;
         this.modele = modele;
         this.dateFabrication = dateFabrication;
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

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public LocalDateTime getDateFabrication(){
        return dateFabrication;
    }

    public void setDateFabrication(LocalDateTime dateFabrication){
        this.dateFabrication = dateFabrication;
    }
}
