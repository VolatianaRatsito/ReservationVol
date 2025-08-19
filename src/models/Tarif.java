package models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tarif {
    private int id;
    private Categorie categorie; 
    private BigDecimal montant;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Vol vol;
    private AvionDetailler avionDetailler;
    private LocalDateTime createdAt;

    public Tarif() {}

    public Tarif(int id, Categorie categorie, BigDecimal montant, LocalDate dateDebut, LocalDate dateFin,
                 Vol vol, AvionDetailler avionDetailler, LocalDateTime createdAt) {
        this.id = id;
        this.categorie = categorie;
        this.montant = montant;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.vol = vol;
        this.avionDetailler = avionDetailler;
        this.createdAt = createdAt;
    }

    // Getters & setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Categorie getCategorie() {
        return categorie;
    }
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public BigDecimal getMontant() {
        return montant;
    }
    public void setMontant(BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant doit être >= 0");
        }
        this.montant = montant;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Vol getVol() {
        return vol;
    }
    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public AvionDetailler getAvionDetailler() {
        return avionDetailler;
    }
    public void setAvionDetailler(AvionDetailler avionDetailler) {
        this.avionDetailler = avionDetailler;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
