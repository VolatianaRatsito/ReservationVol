package utilDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connex.Connexion;
import models.Passager;

public class PassagerDAO {
    
    // Méthode pour se connecter avec email et mot de passe
    public static Passager login(String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM passager WHERE email = ? AND password = ?";
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Passager(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("cin"),
                            rs.getDate("date_naissance"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null;
    }

    // Méthode pour récupérer tous les passagers
    public static List<Passager> getAllPassagers() throws SQLException, ClassNotFoundException {
        List<Passager> passagers = new ArrayList<>();
        String sql = "SELECT * FROM passager";
        
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                passagers.add(new Passager(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("cin"),
                        rs.getDate("date_naissance"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        }
        return passagers;
    }
}
