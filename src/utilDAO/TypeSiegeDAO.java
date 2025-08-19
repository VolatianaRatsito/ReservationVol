package utilDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connex.Connexion;
import models.TypeSiege;

public class TypeSiegeDAO {

    // Méthode pour récupérer tous les sièges depuis la base de données
    public List<TypeSiege> getAllSieges() {
        List<TypeSiege> sieges = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Connexion à la base de données
            conn = Connexion.getConnection();

            String sql = "SELECT id, type_nom FROM type_siege";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Traitement du résultat de la requête
            while (rs.next()) {
                int id = rs.getInt("id");
                String typeNom = rs.getString("type_nom");

                // Créer un objet TypeSiege et l'ajouter à la liste
                TypeSiege typeSiege = new TypeSiege(id, typeNom);
                sieges.add(typeSiege);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            Connexion.closeResources(rs, stmt, conn);
        }

        return sieges;
    }
     public TypeSiege getTypeSiegeById(int id) {
        String sql = "SELECT id, type_nom FROM type_siege WHERE id = ?";
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TypeSiege(rs.getInt("id"), rs.getString("type_nom"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
