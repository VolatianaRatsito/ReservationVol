package models;

import java.time.LocalDateTime;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import connex.Connexion;

public class AnnulationReservation {
    private int id;
    private DemandeReservation demandeReservation;
    private LocalDateTime dateAnnulation;
    private String motif;


    // Constructeurs
    public AnnulationReservation() {}

    public AnnulationReservation(int id, DemandeReservation demandeReservation, LocalDateTime dateAnnulation, String motif) {
        this.id = id;
        this.demandeReservation = demandeReservation;
        this.dateAnnulation = dateAnnulation;
        this.motif = motif;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DemandeReservation getDemandeReservation() {
        return demandeReservation;
    }

    public void setDemandeReservation(DemandeReservation demandeReservation) {
        this.demandeReservation = demandeReservation;
    }

    public LocalDateTime getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(LocalDateTime dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    
}
