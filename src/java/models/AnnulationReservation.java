package models;

import java.util.Date;
import java.util.Calendar;

public class AnnulationReservation {
    private int id;
    private Reservation reservation;
    private Date dateAnnulation;
    private String statut;
    private int heureLimite;

    // Constructeurs
    public AnnulationReservation() {}

    public AnnulationReservation(int id, Reservation reservation, Date dateAnnulation, String statut, int heureLimite) {
        this.id = id;
        this.reservation = reservation;
        this.dateAnnulation = dateAnnulation;
        this.statut = statut;
        this.heureLimite = heureLimite;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Date getDateAnnulation() {
        return dateAnnulation;
    }

    public void setDateAnnulation(Date dateAnnulation) {
        this.dateAnnulation = dateAnnulation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        if (statut.equals("autoriser") || statut.equals("refuser")) {
            this.statut = statut;
        } else {
            throw new IllegalArgumentException("Le statut doit être 'autoriser' ou 'refuser'.");
        }
    }

    public int getHeureLimite() {
        return heureLimite;
    }

    
    public void setHeureLimite(Date dateVol) {
        // Obtenir la date et l'heure actuelles
        Date maintenant = new Date();
    
        // Calculer la limite d'annulation (12 heures avant le vol)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateVol);
        calendar.add(Calendar.HOUR_OF_DAY, -12);
        Date heureLimite = calendar.getTime();
    
        // Vérifier si l'annulation est encore possible
        if (maintenant.before(heureLimite)) {
            this.heureLimite = 12;  // On fixe la limite à 12 heures avant le vol
        } else {
            throw new IllegalArgumentException("L'annulation n'est plus autorisée. La limite de 12 heures avant le vol est dépassée.");
        }
    }
    
    @Override
    public String toString() {
        return "AnnulationReservation{" +
                "id=" + id +
                ", reservation=" + reservation +
                ", dateAnnulation=" + dateAnnulation +
                ", statut='" + statut + '\'' +
                ", heureLimite=" + heureLimite +
                '}';
    }
}
