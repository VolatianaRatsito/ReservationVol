package utilDAO;

import models.VilleDesservie;
import connex.Connexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VilleDesservieDAO {

    /**
     * Récupère toutes les villes desservies de la base de données.
     * @return Liste des objets VilleDesservie.
     */
    public List<VilleDesservie> getAllVillesDesservies() {
        List<VilleDesservie> villesDesservies = new ArrayList<>();
        String sql = "SELECT * FROM ville_desservie";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Parcours du résultat pour ajouter chaque ville desservie à la liste
            while (rs.next()) {
                int id = rs.getInt("id");
                String ville = rs.getString("ville");

                // Créer un objet VilleDesservie et l'ajouter à la liste
                VilleDesservie villeDesservie = new VilleDesservie(id, ville);
                villesDesservies.add(villeDesservie);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return villesDesservies;
    }
    
    /**
     * Ajoute une nouvelle ville desservie dans la base de données.
     * @param ville La ville à ajouter.
     * @return true si l'ajout a réussi, sinon false.
     */
    public boolean addVilleDesservie(String ville) {
        String sql = "INSERT INTO ville_desservie (ville) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isInserted = false;

        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, ville);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isInserted = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(null, stmt, conn);
        }

        return isInserted;
    }

    /**
     * Récupère une ville desservie par son ID.
     * @param id L'ID de la ville à récupérer.
     * @return L'objet VilleDesservie correspondant à l'ID.
     */
    public VilleDesservie getVilleDesservieById(int id) {
        VilleDesservie villeDesservie = null;
        String sql = "SELECT * FROM ville_desservie WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            // Vérifier si une ville avec cet ID existe
            if (rs.next()) {
                int villeId = rs.getInt("id");
                String ville = rs.getString("ville");

                // Créer l'objet VilleDesservie
                villeDesservie = new VilleDesservie(villeId, ville);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return villeDesservie;
    }
}
