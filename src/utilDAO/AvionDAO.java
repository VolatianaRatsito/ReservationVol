package utilDAO;

import models.Avion;
import models.Modele;
import connex.Connexion;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AvionDAO {

    public Avion getAvionById(int id) {
        Avion avion = null;
        String sql = "SELECT * FROM avion WHERE id = ?";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Modele modele = new ModeleDAO().getModeleById(rs.getInt("idmodele"));

                    avion = new Avion(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            modele,
                            rs.getDate("date_fabrication").toLocalDate(),
                            rs.getString("immatriculation"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return avion;
    }

    public List<Avion> getAllAvions() {
        List<Avion> avions = new ArrayList<>();
        String query = "SELECT * FROM avion";

        try (Connection conn = Connexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Modele modele = new ModeleDAO().getModeleById(rs.getInt("idmodele"));

                Avion avion = new Avion(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        modele,
                        rs.getDate("date_fabrication").toLocalDate(),
                        rs.getString("immatriculation"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                avions.add(avion);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return avions;
    }

    
}
