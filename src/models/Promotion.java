package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Promotion {
    private int id;
    private LocalDate datePromotion;
    private LocalDate dateExpiration;
    private Vol vol;
    private AvionDetailler avionSiege;
    private int quantite;
    private BigDecimal pourcentage;

    // Constructeurs
    public Promotion() {}

    public Promotion(int id, LocalDate datePromotion, LocalDate dateExpiration,
                     Vol vol, AvionDetailler avionSiege, int quantite, BigDecimal pourcentage) {
        this.id = id;
        this.datePromotion = datePromotion;
        this.dateExpiration = dateExpiration;
        this.vol = vol;
        this.avionSiege = avionSiege;
        this.quantite = quantite;
        setPourcentage(pourcentage);
    }

    // Getters et setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatePromotion() {
        return datePromotion;
    }
    public void setDatePromotion(LocalDate datePromotion) {
        this.datePromotion = datePromotion;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }
    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Vol getVol() {
        return vol;
    }
    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public AvionDetailler getAvionSiege() {
        return avionSiege;
    }
    public void setAvionSiege(AvionDetailler avionSiege) {
        this.avionSiege = avionSiege;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("Quantité doit être > 0");
        }
        this.quantite = quantite;
    }

    public BigDecimal getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(BigDecimal pourcentage) {
        if (pourcentage.compareTo(BigDecimal.ZERO) < 0 || pourcentage.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Pourcentage doit être entre 0 et 100");
        }
        this.pourcentage = pourcentage.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
