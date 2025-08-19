package utilDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connex.Connexion;
import models.Categorie;

public class CategorieDAO {

    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT id, nom FROM categorie";

        try (Connection conn = Connexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                categories.add(new Categorie(id, nom, 0, 0)); // Valeurs par défaut pour l'âge
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public Categorie getCategorieById(int id) {
        String query = "SELECT id, nom FROM categorie WHERE id = ?";
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categorie(rs.getInt("id"), rs.getString("nom"), 0, 0);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Nouvelle méthode pour définir les âges pour chaque catégorie
    public List<Categorie> getCategoriesWithAgeRanges() {
        List<Categorie> categories = new ArrayList<>();
        categories.add(new Categorie(1, "Enfant", 6, 14));
        categories.add(new Categorie(2, "Jeune", 15, 35));
        categories.add(new Categorie(3, "Adulte", 36, 50));
        categories.add(new Categorie(4, "Plus âgée", 51, Integer.MAX_VALUE));

        return categories;
    }
}