package models;

import java.util.Date;

public class Promotion {
    private int id;
    private Date datePromotion;
    private Date dateExpiration;
    private Vol vol;
    private TypeSiege typeSiege;
    private int nombre;
    private int promotion;

    // Constructeurs
    public Promotion() {}

    public Promotion(int id, Date datePromotion, Date dateExpiration, Vol vol, TypeSiege typeSiege, int nombre, int promotion) {
        this.id = id;
        this.datePromotion = datePromotion;
        this.dateExpiration = dateExpiration;
        this.vol = vol;
        this.typeSiege = typeSiege;
        this.nombre = nombre;
        this.promotion = promotion;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatePromotion() {
        return datePromotion;
    }

    public void setDatePromotion(Date datePromotion) {
        this.datePromotion = datePromotion;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public TypeSiege getTypeSiege() {
        return typeSiege;
    }

    public void setTypeSiege(TypeSiege typeSiege) {
        this.typeSiege = typeSiege;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", datePromotion=" + datePromotion +
                ", dateExpiration=" + dateExpiration +
                ", vol=" + vol +
                ", typeSiege=" + typeSiege +
                ", nombre=" + nombre +
                ", promotion=" + promotion +
                '}';
    }
}
