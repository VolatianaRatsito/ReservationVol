package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Avion {
    private int id;
    private String nom;
    private Modele modele;
    private LocalDate dateFabrication;
    private String immatriculation;
    private LocalDateTime createdAt;

    public Avion() {}

    public Avion(int id) {
        this.id = id;
    }

    public Avion(int id, String nom, Modele modele, LocalDate dateFabrication, String immatriculation, LocalDateTime createdAt) {
        this.id = id;
        this.nom = nom;
        this.modele = modele;
        this.dateFabrication = dateFabrication;
        this.immatriculation = immatriculation;
        this.createdAt = createdAt;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Modele getModele() { return modele; }
    public void setModele(Modele modele) { this.modele = modele; }

    public LocalDate getDateFabrication() { return dateFabrication; }
    public void setDateFabrication(LocalDate dateFabrication) { this.dateFabrication = dateFabrication; }

    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
