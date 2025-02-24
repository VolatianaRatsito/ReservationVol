package models;

import java.math.BigDecimal;

public class VolDetails {
    private int id;
    private Vol vol;
    private int nombreDisponible;
    private AvionDetailler siege;
    private BigDecimal montant;

    // Constructeur
    public VolDetails(int id, Vol vol, int nombreDisponible, AvionDetailler siege, BigDecimal montant) {
        this.id = id;
        this.vol = vol;
        this.nombreDisponible = nombreDisponible;
        this.siege = siege;
        this.montant = montant;
    }

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
        this.nombreDisponible = nombreDisponible;
    }

    public AvionDetailler getSiege() {
        return siege;
    }

    public void setSiege(AvionDetailler siege) {
        this.siege = siege;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant ne peut pas être négatif");
        }
        this.montant = montant;
    }

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "VolDetails{" +
                "id=" + id +
                ", vol=" + vol +
                ", nombreDisponible=" + nombreDisponible +
                ", siege=" + siege +
                ", montant=" + montant +
                '}';
    }
}
