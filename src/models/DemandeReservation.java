package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DemandeReservation {
    private int id;
    private Passager passager;
    private Vol vol;
    private AvionDetailler  avionDetailler ;
    private Tarif tarif;
    private int quantite;
    private ModePaiement modePaiement;
    private LocalDateTime dateDemande;
    private BigDecimal montantTotal;
    private String statut;

    // Constructeurs
    public DemandeReservation() {}

    public DemandeReservation(int id, Passager passager, Vol vol, AvionDetailler  avionDetailler , Tarif tarif, int quantite,
                              ModePaiement modePaiement, LocalDateTime dateDemande, BigDecimal montantTotal) {
        this.id = id;
        this.passager = passager;
        this.vol = vol;
        this.avionDetailler = avionDetailler;
        this.tarif = tarif;
        this.quantite = quantite;
        this.modePaiement = modePaiement;
        this.dateDemande = dateDemande;
        this.montantTotal = montantTotal;
    }

    // Getters & Setters
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

    public AvionDetailler getAvionDetailler() {
        return avionDetailler;
    }

    public void setAvionDetailler(AvionDetailler avionDetailler) {
        this.avionDetailler = avionDetailler;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0.");
        }
        this.quantite = quantite;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return (statut == null || statut.isEmpty()) ? "CONFIRMEE" : statut;
    }

    public void setStatut(String statut) {
        if (statut == null || statut.isEmpty()) {
            this.statut = "CONFIRMEE";
        } else {
            this.statut = statut;
        }
    }

}
