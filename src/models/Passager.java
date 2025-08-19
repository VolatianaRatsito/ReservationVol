package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connex.Connexion;

public class Passager {
    private int id;

    private String nom;

    private String cin;

    private Categorie categorie;

    private String email;

    private String password;

    // Constructeurs
    public Passager(int id, String nom, String cin, Categorie categorie, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.cin = cin;
        this.categorie = categorie;
        this.email = email;
        this.password = password;
    }

    public Passager(int id){this.id = id;}

    public Passager(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Passager() {}

    // Getters et Setters
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Méthode pour vérifier le login
    public boolean login(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM passager WHERE email = ? AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.email);
            stmt.setString(2, this.password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Résultat SQL : " + count); // Debug
                    return count > 0;
                }
            }
        }
        return false;
    }

    // Méthode pour récupérer tous les passagers avec leur catégorie
    public static List<Passager> getAllPassagers() throws SQLException, ClassNotFoundException {
        List<Passager> passagers = new ArrayList<>();
        String sql = "SELECT p.*, c.id AS cat_id, c.nom AS cat_nom, c.age_min, c.age_max " +
                    "FROM passager p " +
                    "JOIN categorie c ON p.idCategorie = c.id";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categorie categorie = new Categorie(
                    rs.getInt("cat_id"), 
                    rs.getString("cat_nom"),
                    rs.getInt("age_min"),
                    rs.getInt("age_max")
                );

                passagers.add(new Passager(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("cin"),
                    categorie,
                    rs.getString("email"),
                    rs.getString("password")
                ));
            }
        }
        return passagers;
    }


    // Méthode pour récupérer un passager par son ID
    public static Passager getPassagerById(int passagerId) throws SQLException, ClassNotFoundException {
        Passager passager = null;
        String sql = "SELECT p.*, c.id AS cat_id, c.nom AS cat_nom, c.age_min, c.age_max " +
                    "FROM passager p " +
                    "JOIN categorie c ON p.idCategorie = c.id " +
                    "WHERE p.id = ?";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, passagerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categorie categorie = new Categorie(
                        rs.getInt("cat_id"), 
                        rs.getString("cat_nom"),
                        rs.getInt("age_min"),
                        rs.getInt("age_max")
                    );

                    passager = new Passager(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("cin"),
                        categorie,
                        rs.getString("email"),
                        rs.getString("password")
                    );
                }
            }
        }
        return passager;
    }

}