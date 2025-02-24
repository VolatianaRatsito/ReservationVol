package models;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private Passager passager;
    private Vol vol;
    private LocalDate  dateVol ;
    private int nombrePlaces;
    private LocalDate  dateCreation;

    // Constructeurs
    public Reservation() {}

   public Reservation(int id, Passager passager, Vol vol, LocalDate  dateVol , int nombrePlaces, LocalDate  dateCreation) {
        this.id = id;
        this.passager = passager;
        this.vol = vol;
        this.dateVol = dateVol;
        this.nombrePlaces = nombrePlaces;
        this. dateCreation =  dateCreation;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Passager getPassager() {
        return passager;
    }

    public void setPassager(Passager passager) {
        this.passager = passager;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public LocalDate getDateVol() {
        return dateVol;
    }

    public void setDateVol(LocalDate dateVol) {
        this.dateVol = dateVol;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public LocalDate getDateCreation () {
        return  dateCreation ;
    }

    public void setDateCreation (LocalDate  dateCreation ) {
        this. dateCreation  =  dateCreation ;
    }
}
