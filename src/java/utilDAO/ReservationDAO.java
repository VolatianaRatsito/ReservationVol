package utilDAO;

import models.*;
import connex.Connexion;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationDAO {

    // Vérifier si le vol est disponible
    public boolean verifierDisponibilite(Vol vol, LocalDate dateVol) {
        String query = "SELECT COUNT(*) FROM reservation WHERE idVol = ? AND dateVol = ?";
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vol.getId());
            stmt.setDate(2, Date.valueOf(dateVol));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) == 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Vérifier la date limite de réservation
    public boolean verifierDateLimiteReservation(Vol vol, LocalDate dateReservation) {
        LocalDate dateLimite = vol.getDateDepart().toLocalDate().minusDays(1);
        return !dateReservation.isAfter(dateLimite);
    }


    // Vérifier la disponibilité du siège
    public boolean verifierDisponibiliteSiege(Vol vol, int siege) {
        String query = "SELECT nombre_disponible FROM vol_details WHERE idVol = ? AND idSiege = ?";
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vol.getId());
            stmt.setInt(2, siege);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt("nombre_disponible") > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Insérer une réservation
    public boolean insererReservation(Reservation reservation) {
        String query = "INSERT INTO reservation (idPassager, idVol, dateVol, nombre_places, date_creation) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservation.getPassager().getId());
            stmt.setInt(2, reservation.getVol().getId());
            stmt.setDate(3, Date.valueOf(reservation.getDateVol()));
            stmt.setInt(4, reservation.getNombrePlaces());
            stmt.setDate(6, Date.valueOf(reservation.getDateCreation()));
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // public boolean reserverVol(Passager principalPassager, Vol vol, LocalDate dateReservation, int nombrePlaces, List<Integer> sieges) {
    //     // Vérifier la disponibilité du vol
    //     if (!verifierDisponibilite(vol, dateReservation)) {
    //         System.out.println("Le vol n'est pas disponible à cette date.");
    //         return false;
    //     }

    //     // Vérifier la date limite de réservation
    //     if (!verifierDateLimiteReservation(vol, dateReservation)) {
    //         System.out.println("La date limite de réservation a été dépassée.");
    //         return false;
    //     }

    //     // Créer la réservation principale
    //     Reservation reservation = new Reservation();
    //     reservation.setPassager(principalPassager);
    //     reservation.setVol(vol);
    //     reservation.setDateVol(dateReservation);
    //     reservation.setNombrePlaces(nombrePlaces);
    //     reservation.setDateCreation(LocalDate.now());

    //     // Ajouter les détails de réservation pour chaque siège
    //     for (Integer siege : sieges) {
    //         if (verifierDisponibiliteSiege(vol, siege)) {
    //             TypeSiege typeSiege = new TypeSiege(); // Vérifiez si une association avec VolDetails est nécessaire
                
    //             // Récupérer les détails du vol pour ce siège
    //             AvionDetailler aviondetails = new AvionDetailler();
    //             VolDetailsDAO volDetails =  new VolDetailsDAO();
    //             VolDetails volDetails2 = new VolDetails();
    //             volDetails.getVolDetailsForSeat(vol, aviondetails);

    //             // Créer un objet ReservationDetail
    //             ReservationDetail detail = new ReservationDetail();
    //             detail.setSiege(typeSiege);
    //             detail.setReservation(reservation);
    //             detail.setPassager(principalPassager);
    //             detail.setVolDetails(volDetails2);
    //             detail.ajouterDetail(detail);
    //         } else {
    //             System.out.println("Le siège " + siege + " n'est pas disponible.");
    //             return false;
    //         }
    //     }

    //     // Insérer la réservation principale dans la base de données
    //     if (!insererReservation(reservation)) {
    //         System.out.println("Erreur lors de l'insertion de la réservation.");
    //         return false;
    //     }

    //     // Ajouter les passagers accompagnants
        
    //     for (int i = 1; i < nombrePlaces; i++) {
    //         Passager accompagnant = new Passager(principalPassager.getId(), "Accompagnant " + i, 
    //                                             principalPassager.getCin(), principalPassager.getDateNaissance(), 
    //                                             principalPassager.getEmail(), principalPassager.getPassword());
            
    //         ReservationAccompagnantDAO accompagnantDAO = new ReservationAccompagnantDAO();
    //         ReservationAccompagnant reservationAccompagnant = new ReservationAccompagnant(reservation, accompagnant);
            
    //         try {
    //             accompagnantDAO.addReservationAccompagnant(reservationAccompagnant);
    //         } catch (SQLException | ClassNotFoundException e) {
    //             e.printStackTrace();
    //             return false; 
    //         }
    //     }

    //     return true;
    // }

}
