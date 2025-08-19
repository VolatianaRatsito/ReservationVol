package utilDAO;

import connex.Connexion;
import models.Nationalite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NationaliteDAO {
    public Nationalite getNationaliteById(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, nom FROM nationalite WHERE id = ?";
        
        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Nationalite(
                        rs.getInt("id"),
                        rs.getString("nom")
                    );
                }
            }
        }
        return null;
    }
    public List<Nationalite> getAllNationalites() {
        List<Nationalite> nationalites = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT id, nom FROM nationalite";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                Nationalite nat = new Nationalite(id, nom);
                nationalites.add(nat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return nationalites;
    }
}
