package models;

import java.sql.Date;
import java.io.*;

public class Passport {
    private int id;
    private String numero;
    private Nationalite nationalite;
    private Date dateEmission;
    private Date dateExpiration;
    private Passager passager;
    private String photo;  // Changement de byte[] à String

    // === Constructeurs ===
    public Passport() {}

    public Passport(int id, String numero, Nationalite nationalite, 
                   Date dateEmission, Date dateExpiration, 
                   Passager passager, String photo) {
        this.id = id;
        this.numero = numero;
        this.nationalite = nationalite;
        this.dateEmission = dateEmission;
        this.dateExpiration = dateExpiration;
        this.passager = passager;
        this.photo = photo;
    }

    public Passport(Nationalite nationalite, Date dateEmission, 
                   Date dateExpiration, Passager passager, 
                   String photo) {
        this.nationalite = nationalite;
        this.dateEmission = dateEmission;
        this.dateExpiration = dateExpiration;
        this.passager = passager;
        this.photo = photo;
    }

    // === Getters & Setters ===

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public Date getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Passager getPassager() {
        return passager;
    }

    public void setPassager(Passager passager) {
        this.passager = passager;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}