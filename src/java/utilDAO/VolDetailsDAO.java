package utilDAO;

import models.Vol;
import models.AvionDetailler;
import models.VolDetails;
import connex.Connexion;

import java.math.BigDecimal;
import java.sql.*;

public class VolDetailsDAO {

    public VolDetails insererVolDetails(Vol vol, int nombreDisponible, AvionDetailler siege, BigDecimal montant) {
        int id = -1;
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO vol_details (idVol, nombre_disponible, idSiege, montant) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            
            conn.setAutoCommit(false);
            
            stmt.setInt(1, vol.getId());
            stmt.setInt(2, nombreDisponible);
            stmt.setInt(3, siege.getId());
            stmt.setBigDecimal(4, montant);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }
            }
            
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Log more information about the error
        }
        
        return id != -1 ? new VolDetails(id, vol, nombreDisponible, siege, montant) : null;
    }
    

    public VolDetails getVolDetails(Vol vol, AvionDetailler siege) {
        String sql = "SELECT id, idVol, nombre_disponible, idSiege, montant FROM vol_details WHERE idVol = ? AND idSiege = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Connexion à la base de données
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vol.getId());  // Paramètre pour l'idVol
            stmt.setInt(2, siege.getId());  // Paramètre pour l'idSiege
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Récupération des données de la requête
                int id = rs.getInt("id");
                int nombreDisponible = rs.getInt("nombre_disponible");
                BigDecimal montant = rs.getBigDecimal("montant");
                
                // Retourner un nouvel objet VolDetails avec les données récupérées
                return new VolDetails(id, vol, nombreDisponible, siege, montant);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            Connexion.closeResources(rs, stmt, conn);
        }
        
        return null;  // Si aucun résultat trouvé
    }
    

    public VolDetails getVolDetailsForSeat(Vol vol, AvionDetailler siege) {
        String sql = "SELECT id, idVol, nombre_disponible, idSiege, montant FROM vol_details WHERE idVol = ? AND idSiege = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vol.getId());
            stmt.setInt(2, siege.getId());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                int nombreDisponible = rs.getInt("nombre_disponible");
                BigDecimal montant = rs.getBigDecimal("montant");
                
                return new VolDetails(id, vol, nombreDisponible, siege, montant);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }
        return null;
    }
}
