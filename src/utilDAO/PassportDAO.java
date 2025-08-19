package utilDAO;

import connex.Connexion;
import models.Nationalite;
import models.Passager;
import models.Passport;
import models.Vol;
import models.Categorie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PassportDAO {

    // === Insertion d'un passeport ===
    public void insertPassport(Passport passport) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO passport (idNationalite, date_emission, date_expiration, idPassager, photo) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, passport.getNationalite().getId());
            stmt.setDate(2, passport.getDateEmission());
            stmt.setDate(3, passport.getDateExpiration());
            stmt.setInt(4, passport.getPassager().getId());
            stmt.setString(5, passport.getPhoto()); // Nom du fichier photo

            stmt.executeUpdate();
            System.out.println("✅ Passeport inséré avec succès !");
        }
    }

    // === Lecture de tous les passeports ===
    public List<Passport> getAllPassports() throws SQLException, ClassNotFoundException {
        List<Passport> passports = new ArrayList<>();

        String sql = """
                SELECT p.id, p.numero, p.date_emission, p.date_expiration, p.photo,
                       n.id AS nationalite_id, n.nom AS nationalite_nom,
                       ps.id AS passager_id, ps.nom AS passager_nom, ps.cin, ps.email, ps.password,
                       c.id AS cat_id, c.nom AS cat_nom, c.age_min, c.age_max
                FROM passport p
                JOIN nationalite n ON p.idNationalite = n.id
                JOIN passager ps ON p.idPassager = ps.id
                JOIN categorie c ON ps.idCategorie = c.id
                """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Créer les objets nécessaires
                Nationalite nat = new Nationalite(rs.getInt("nationalite_id"), rs.getString("nationalite_nom"));

                Categorie cat = new Categorie(
                        rs.getInt("cat_id"),
                        rs.getString("cat_nom"),
                        rs.getInt("age_min"),
                        rs.getInt("age_max")
                );

                Passager passager = new Passager(
                        rs.getInt("passager_id"),
                        rs.getString("passager_nom"),
                        rs.getString("cin"),
                        cat,
                        rs.getString("email"),
                        rs.getString("password")
                );

                Passport passport = new Passport(
                        rs.getInt("id"),
                        rs.getString("numero"),
                        nat,
                        rs.getDate("date_emission"),
                        rs.getDate("date_expiration"),
                        passager,
                        rs.getString("photo")
                );

                passports.add(passport);
            }
        }

        return passports;
    }

    // === Lecture des passeports d’un passager spécifique ===
    public List<Passport> getPassportsByPassenger(int passagerId) throws SQLException, ClassNotFoundException {
        List<Passport> passports = new ArrayList<>();

        String sql = """
                SELECT p.id, p.numero, p.date_emission, p.date_expiration, p.photo,
                       n.id AS nationalite_id, n.nom AS nationalite_nom
                FROM passport p
                JOIN nationalite n ON p.idNationalite = n.id
                WHERE p.idPassager = ?
                """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, passagerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Nationalite nat = new Nationalite(
                            rs.getInt("nationalite_id"),
                            rs.getString("nationalite_nom")
                    );

                    Passport passport = new Passport(
                            rs.getInt("id"),
                            rs.getString("numero"),
                            nat,
                            rs.getDate("date_emission"),
                            rs.getDate("date_expiration"),
                            null, // Passager non chargé ici
                            rs.getString("photo")
                    );

                    passports.add(passport);
                }
            }
        }

        return passports;
    }

    // === Récupération d’un passeport par ID ===
    public Passport getById(int idPassport) throws SQLException, ClassNotFoundException {
        Passport passport = null;

        String sql = """
            SELECT p.id, p.numero, p.date_emission, p.date_expiration, p.photo,
                   n.id AS nationalite_id, n.nom AS nationalite_nom,
                   ps.id AS passager_id, ps.nom AS passager_nom, ps.cin, ps.email, ps.password,
                   c.id AS cat_id, c.nom AS cat_nom, c.age_min, c.age_max
            FROM passport p
            JOIN nationalite n ON p.idNationalite = n.id
            JOIN passager ps ON p.idPassager = ps.id
            JOIN categorie c ON ps.idCategorie = c.id
            WHERE p.id = ?
        """;

        try (
            Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, idPassport);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Nationalite nat = new Nationalite(rs.getInt("nationalite_id"), rs.getString("nationalite_nom"));

                    Categorie cat = new Categorie(
                            rs.getInt("cat_id"),
                            rs.getString("cat_nom"),
                            rs.getInt("age_min"),
                            rs.getInt("age_max")
                    );

                    Passager passager = new Passager(
                            rs.getInt("passager_id"),
                            rs.getString("passager_nom"),
                            rs.getString("cin"),
                            cat,
                            rs.getString("email"),
                            rs.getString("password")
                    );

                    passport = new Passport(
                            rs.getInt("id"),
                            rs.getString("numero"),
                            nat,
                            rs.getDate("date_emission"),
                            rs.getDate("date_expiration"),
                            passager,
                            rs.getString("photo")
                    );
                }
            }
        }

        return passport;
    }

    // === Vérifie si un passeport est valide pour un vol ===
    public boolean isPassportValidePourVol(Passport passport, Vol vol, boolean exigenceSixMois) {
        if (passport == null || passport.getDateExpiration() == null || vol == null || vol.getDateDepart() == null) {
            System.out.println(" Données incomplètes pour valider le passeport.");
            return false;
        }

        LocalDate dateExpiration = passport.getDateExpiration().toLocalDate();
        LocalDate dateVol = vol.getDateDepart().toLocalDate();
        LocalDate dateAujourdHui = LocalDate.now();

        if (dateExpiration.isBefore(dateAujourdHui)) {
            System.out.println(" Le passeport est déjà expiré.");
            return false;
        }

        if (dateExpiration.isBefore(dateVol)) {
            System.out.println(" Le passeport expire avant la date du vol.");
            return false;
        }

        if (exigenceSixMois) {
            LocalDate dateLimite = dateVol.plusMonths(6);
            if (dateExpiration.isBefore(dateLimite)) {
                System.out.println(" Le passeport doit être valide au moins 6 mois après la date du vol.");
                return false;
            }
        }

        return true;
    }
}
