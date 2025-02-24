package utilDAO;

import models.Avion;
import models.Modele;
import connex.Connexion;

import java.sql.*;
import java.time.LocalDateTime;

public class AvionDAO {

    /**
     * Récupère un avion en fonction de son ID.
     * @param id L'ID de l'avion à récupérer.
     * @return L'objet Avion correspondant à l'ID, ou null si l'avion n'existe pas.
     */
    public Avion getAvionById(int id) {
        Avion avion = null;
        String sql = "SELECT * FROM avion WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            // Vérifier si un avion a été trouvé
            if (rs.next()) {
                int avionId = rs.getInt("id");
                String nom = rs.getString("nom");
                Modele modele = new ModeleDAO().getModeleById(rs.getInt("idModele")); 
                Timestamp dateFabricationTimestamp = rs.getTimestamp("date_fabrication");
                LocalDateTime dateFabrication = (dateFabricationTimestamp != null) ? dateFabricationTimestamp.toLocalDateTime() : null;
                avion = new Avion(avionId, nom,modele, dateFabrication);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return avion;
    }
}
