package models;

public class ReservationDetail {
    
    private int id;
    private Reservation reservation;
    private Passager passager;
    private TypeSiege siege; 
    private VolDetails volDetails;

    // Constructeurs
    public ReservationDetail() {}

    public ReservationDetail(int id, Reservation reservation, Passager passager,TypeSiege siege,VolDetails volDetails) {
        this.id = id;
        this.reservation = reservation;
        this.passager = passager;
        this.siege = siege;
        this. volDetails =  volDetails;
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

    public Passager getPassager() {
        return passager;
    }

    public void setPassager(Passager passager) {
        this.passager = passager;
    }

    public TypeSiege getSiege() {
        return siege;
    }

    public void setSiege(TypeSiege siege) {
        this.siege = siege;
    }

    public VolDetails getVolDetails() {
        return volDetails;
    }

    public void setVolDetails(VolDetails volDetails) {
        this.volDetails = volDetails;
    }
}
