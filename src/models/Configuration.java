package models;

import connex.Connexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private int id;
    private int heureLimiteReservation;
    private int heureLimiteAnnulation; 
    private Timestamp dateCreation;

    // Constructeurs
    public Configuration() {}

    public Configuration(int heureLimiteReservation, int heureLimiteAnnulation) {
        this.heureLimiteReservation = heureLimiteReservation;
        this.heureLimiteAnnulation = heureLimiteAnnulation;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeureLimiteReservation() {
        return heureLimiteReservation;
    }

    public void setHeureLimiteReservation(int heureLimiteReservation) {
        this.heureLimiteReservation = heureLimiteReservation;
    }

    public int getHeureLimiteAnnulation() {
        return heureLimiteAnnulation;
    }

    public void setHeureLimiteAnnulation(int heureLimiteAnnulation) {
        this.heureLimiteAnnulation = heureLimiteAnnulation;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    // Méthode pour enregistrer une configuration
    public void save() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = Connexion.getConnection();
            String sql = "INSERT INTO reservation_configuration (heure_limite_reservation, heure_limite_annulation) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, this.heureLimiteReservation);
            stmt.setInt(2, this.heureLimiteAnnulation);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de la configuration, aucune ligne affectée.");
            }
            
            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Échec de la création de la configuration, aucun ID obtenu.");
            }
        } finally {
            Connexion.closeResources(generatedKeys, stmt, conn);
        }
    }

    // Méthode pour récupérer toutes les configurations
    public static List<Configuration> getAll() throws SQLException, ClassNotFoundException {
        List<Configuration> configurations = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM reservation_configuration ORDER BY date_creation DESC");

            while (rs.next()) {
                Configuration config = new Configuration();
                config.setId(rs.getInt("id"));
                config.setHeureLimiteReservation(rs.getInt("heure_limite_reservation"));
                config.setHeureLimiteAnnulation(rs.getInt("heure_limite_annulation")); 
                config.setDateCreation(rs.getTimestamp("date_creation"));
                configurations.add(config);
            }
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }
        return configurations;
    }

    // Méthode pour récupérer la configuration actuelle
    public static Configuration getCurrent() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Configuration config = null;

        try {
            conn = Connexion.getConnection();
            String sql = "SELECT * FROM reservation_configuration ORDER BY date_creation DESC LIMIT 1";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                config = new Configuration();
                config.setId(rs.getInt("id"));
                config.setHeureLimiteReservation(rs.getInt("heure_limite_reservation"));
                config.setHeureLimiteAnnulation(rs.getInt("heure_limite_annulation"));
                config.setDateCreation(rs.getTimestamp("date_creation"));
            }
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }
        return config;
    }
    
    // Méthode pour mettre à jour une configuration
    public void update() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Connexion.getConnection();
            String sql = "UPDATE reservation_configuration SET heure_limite_reservation = ?, heure_limite_annulation = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.heureLimiteReservation);
            stmt.setInt(2, this.heureLimiteAnnulation);
            stmt.setInt(3, this.id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la mise à jour, aucune configuration trouvée avec l'ID: " + this.id);
            }
        } finally {
            Connexion.closeResources(null, stmt, conn);
        }
    }
}
