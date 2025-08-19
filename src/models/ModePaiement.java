package models;

import connex.Connexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModePaiement {
    private int id;
    private String nom;

    public ModePaiement() {
    }

    public ModePaiement(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode statique pour récupérer tous les modes de paiement
    public static List<ModePaiement> getAllModePaiement() {
        List<ModePaiement> modes = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Connexion.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT id, nom FROM mode_paiement";
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                modes.add(new ModePaiement(id, nom));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }
        
        return modes;
    }

      public static ModePaiement getById(int id) {
        String sql = "SELECT id, nom FROM mode_paiement WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new ModePaiement(
                    rs.getInt("id"),
                    rs.getString("nom")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }
        return null;
    }
}