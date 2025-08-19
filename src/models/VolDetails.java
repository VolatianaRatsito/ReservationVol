package models;

import java.math.BigDecimal;

public class VolDetails {
    private int id;
    private Vol vol;
    private int nombreDisponible;
    private AvionDetailler siege;
    private Tarif tarif;

    public VolDetails(int id, Vol vol, int nombreDisponible, AvionDetailler siege, Tarif tarif) {
        this.id = id;
        this.vol = vol;
        this.setNombreDisponible(nombreDisponible);
        this.siege = siege;
        this.tarif = tarif;
    }

    // Constructeur par défaut
    public VolDetails() {
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public int getNombreDisponible() {
        return nombreDisponible;
    }

    public void setNombreDisponible(int nombreDisponible) {
        if (nombreDisponible < 0) {
            throw new IllegalArgumentException("Le nombre disponible ne peut pas être négatif");
        }
        this.nombreDisponible = nombreDisponible;
    }

    public AvionDetailler getSiege() {
        return siege;
    }

    public void setSiege(AvionDetailler siege) {
        this.siege = siege;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

}