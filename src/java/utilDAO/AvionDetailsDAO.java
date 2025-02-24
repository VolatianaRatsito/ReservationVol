package utilDAO;

import models.AvionDetailler;
import models.Avion;
import models.TypeSiege;
import connex.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvionDetailsDAO {

    public AvionDetailler getAvionDetaillerByAvionAndSiege(int idAvion, int idSiege) {
        String sql = "SELECT ad.id, ad.idAvion, ad.idSiege, ad.nombre_siege, t.types AS siegeType, a.nom AS avionNom " +
                     "FROM avion_detailler ad " +
                     "JOIN avion a ON ad.idAvion = a.id " +
                     "JOIN type_siege t ON ad.idSiege = t.id " +
                     "WHERE ad.idAvion = ? AND ad.idSiege = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Connexion à la base de données
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAvion);  // Paramètre pour l'idAvion
            stmt.setInt(2, idSiege);  // Paramètre pour l'idSiege
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Récupérer les données de la requête
                int id = rs.getInt("id");
                int nombreSiege = rs.getInt("nombre_siege");
                String siegeType = rs.getString("siegeType");
                String avionNom = rs.getString("avionNom");

                // Créer les objets nécessaires pour AvionDetailler
                Avion avion = new Avion();
                avion.setId(idAvion);
                avion.setNom(avionNom);  // On peut imaginer que l'objet `Avion` a un setter pour le nom
                
                TypeSiege typeSiege = new TypeSiege();
                typeSiege.setId(idSiege);
                typeSiege.setTypes(siegeType);  // On suppose que l'objet `TypeSiege` a un setter pour le type
                
                // Retourner l'objet AvionDetailler
                return new AvionDetailler(id, avion, typeSiege, nombreSiege);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            Connexion.closeResources(rs, stmt, conn);
        }
        
        return null;  // Si aucun résultat n'est trouvé
    }
}
