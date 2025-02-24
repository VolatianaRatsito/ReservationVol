package utilDAO;

import java.sql.*;

import models.*;
import connex.Connexion;

public class ReservationAccompagnantDAO {

    private static final String INSERT_QUERY = "INSERT INTO reservation_accompagnant (idReservation, idPassager) VALUES (?, ?)";

    public void addReservationAccompagnant(ReservationAccompagnant reservationAccompagnant) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Obtenir une connexion via la classe Connexion
            conn = Connexion.getConnection();

            stmt = conn.prepareStatement(INSERT_QUERY);

            stmt.setInt(1, reservationAccompagnant.getReservation().getId());
            stmt.setInt(2, reservationAccompagnant.getPassager().getId());

            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'ajout de la réservation accompagnant.");
        } finally {
            Connexion.closeResources(null, stmt, conn);
        }
    }
}

