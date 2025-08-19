package utilDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connex.Connexion;
import models.Modele;

public class ModeleDAO {

     /**  Méthode pour obtenir un modèle par son ID */
    public Modele getModeleById(int id) {
        Modele modele = null;
        String sql = "SELECT id, nom FROM modele WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); 
            rs = stmt.executeQuery();

            // Vérifier si un modèle avec cet ID existe
            if (rs.next()) {
                int modeleId = rs.getInt("id");
                String modeleNom = rs.getString("nom");

                modele = new Modele(modeleId, modeleNom);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return modele;
    }
}
