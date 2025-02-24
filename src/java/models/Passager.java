/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.util.Date;
/**
 *
 * @author Anna
 */


public class Passager {
    private int id;
    private String nom;
    private String cin;
    private Date dateNaissance;
    private String email;
    private String  password;

    // Constructeur
    public Passager(int id, String nom, String cin, Date dateNaissance, String email, String  password) {
        this.id = id;
        this.nom = nom;
        this.cin = cin;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this. password =  password;
    }

    public Passager() {
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return  password;
    }

    public void setPassword(String  password) {
        this. password =  password;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", cin='" + cin + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", email='" + email + '\'' +
                ",  password='" +  password + '\'' +
                '}';
    }
}
