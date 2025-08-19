package utilDAO;

import models.AvionDetailler;
import models.Promotion;
import models.Vol;
import models.VolDetails;
import models.TypeSiege;
import connex.Connexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;  // <-- Ajouté
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PromotionDAO {

    // Ajouter une promotion
      // Ajouter une promotion
    public Promotion addPromotion(LocalDate datePromotion, LocalDate dateExpiration,
                                  Vol vol, AvionDetailler avionSiege, int quantite, BigDecimal pourcentage) {
        String sql = "INSERT INTO promotion (date_promotion, date_expiration, id_vol, id_avion_siege, quantite, pourcentage) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        Promotion promotion = new Promotion();

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(datePromotion));
            stmt.setDate(2, Date.valueOf(dateExpiration));
            stmt.setInt(3, vol.getId());
            stmt.setInt(4, avionSiege.getId());
            stmt.setInt(5, quantite);
            stmt.setBigDecimal(6, pourcentage);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        promotion.setId(keys.getInt(1));
                    }
                }
                promotion.setDatePromotion(datePromotion);
                promotion.setDateExpiration(dateExpiration);
                promotion.setVol(vol);
                promotion.setAvionSiege(avionSiege);
                promotion.setQuantite(quantite);
                promotion.setPourcentage(pourcentage);
                return promotion;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lister toutes les promotions
    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        String sql = """
            SELECT p.id, p.date_promotion, p.date_expiration, p.quantite, p.pourcentage,
                   v.id AS vol_id, v.nom AS vol_nom,
                   a.id AS avion_id, a.nombre_siege,
                   ts.id AS type_siege_id, ts.type_nom
            FROM promotion p
            JOIN vol v ON p.id_vol = v.id
            JOIN avion_detailler a ON p.id_avion_siege = a.id
            JOIN type_siege ts ON a.id_type_siege = ts.id
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Promotion p = new Promotion();
                p.setId(rs.getInt("id"));
                p.setDatePromotion(rs.getDate("date_promotion").toLocalDate());
                p.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                p.setQuantite(rs.getInt("quantite"));
                p.setPourcentage(rs.getBigDecimal("pourcentage"));

                Vol vol = new Vol();
                vol.setId(rs.getInt("vol_id"));
                vol.setNom(rs.getString("vol_nom"));
                p.setVol(vol);

                TypeSiege ts = new TypeSiege();
                ts.setId(rs.getInt("type_siege_id"));
                ts.setTypeNom(rs.getString("type_nom"));

                AvionDetailler avion = new AvionDetailler();
                avion.setId(rs.getInt("avion_id"));
                avion.setNombre_siege(rs.getInt("nombre_siege"));
                avion.setTypeSiege(ts);

                p.setAvionSiege(avion);

                promotions.add(p);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    // Méthode pour obtenir une promotion par ID
    public Promotion getPromotionById(int id) {
        String sql = """
            SELECT p.*, v.nom AS vol_nom,
                   a.nombre_siege,
                   ts.id AS type_siege_id, ts.type_nom
            FROM promotion p
            JOIN vol v ON p.id_vol = v.id
            JOIN avion_detailler a ON p.id_avion_siege = a.id
            JOIN type_siege ts ON a.id_type_siege = ts.id
            WHERE p.id = ?
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPromotionFromResultSet(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Promotion> getPromotionsByVolAndSiege(Vol vol, TypeSiege typeSiege) {
        List<Promotion> promotions = new ArrayList<>();
        String query = """
            SELECT p.*
            FROM promotion p
            JOIN avion_detailler a ON p.id_avion_siege = a.id
            WHERE p.id_vol = ? AND a.id_type_siege = ?
              AND p.date_promotion <= CURRENT_DATE
              AND p.date_expiration >= CURRENT_DATE
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, vol.getId());
            stmt.setInt(2, typeSiege.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Promotion promotion = new Promotion(
                            rs.getInt("id"),
                            rs.getDate("date_promotion").toLocalDate(),
                            rs.getDate("date_expiration").toLocalDate(),
                            vol,
                            null,
                            rs.getInt("quantite"),
                            rs.getBigDecimal("pourcentage")
                    );
                    promotions.add(promotion);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    // Vérifier la disponibilité
    public boolean isPromotionAvailable(int volId, int typeSiegeId) {
        String sql = """
            SELECT COUNT(*)
            FROM promotion p
            JOIN avion_detailler a ON p.id_avion_siege = a.id
            WHERE p.id_vol = ? AND a.id_type_siege = ?
              AND p.date_promotion <= CURRENT_DATE
              AND p.date_expiration >= CURRENT_DATE
              AND p.quantite > 0
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, volId);
            stmt.setInt(2, typeSiegeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Diminuer la quantité dispo après réservation
   public boolean decreaseAvailablePromotionSeats(int promotionId, int quantite) {
        String sql = "UPDATE promotion SET quantite = quantite - ? WHERE id = ? AND quantite >= ?";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantite);
            stmt.setInt(2, promotionId);
            stmt.setInt(3, quantite);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Supprimer une promotion
    public boolean deletePromotion(int id) {
        String sql = "DELETE FROM promotion WHERE id = ?";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mapper ResultSet -> Promotion
       private Promotion mapPromotionFromResultSet(ResultSet rs) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setId(rs.getInt("id"));
        promotion.setDatePromotion(rs.getDate("date_promotion").toLocalDate());
        promotion.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
        promotion.setQuantite(rs.getInt("quantite"));
        promotion.setPourcentage(rs.getBigDecimal("pourcentage"));

        Vol vol = new Vol();
        vol.setId(rs.getInt("id_vol"));
        vol.setNom(rs.getString("vol_nom"));
        promotion.setVol(vol);

        TypeSiege typeSiege = new TypeSiege();
        typeSiege.setId(rs.getInt("type_siege_id"));
        typeSiege.setTypeNom(rs.getString("type_nom"));

        AvionDetailler avion = new AvionDetailler();
        avion.setId(rs.getInt("id_avion_siege"));
        avion.setNombre_siege(rs.getInt("nombre_siege"));
        avion.setTypeSiege(typeSiege);

        promotion.setAvionSiege(avion);

        return promotion;
    }

    // Appliquer promotion
    public static BigDecimal appliquerPromotion(VolDetails volDetails, List<Promotion> promotions) {
        if (promotions == null || promotions.isEmpty()) {
            return volDetails.getTarif().getMontant();
        }

        for (Promotion promotion : promotions) {
            if (estPromotionApplicable(volDetails, promotion)) {
                BigDecimal reduction = volDetails.getTarif().getMontant()
                        .multiply(promotion.getPourcentage())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

                BigDecimal montantApresReduction = volDetails.getTarif().getMontant().subtract(reduction);

                // Décrémenter la quantité
                promotion.setQuantite(promotion.getQuantite() - 1);

                return montantApresReduction;
            }
        }

        return volDetails.getTarif().getMontant();
    }

    private static boolean estPromotionApplicable(VolDetails volDetails, Promotion promotion) {
        LocalDate maintenant = LocalDate.now();

        return promotion.getVol().getId() == volDetails.getVol().getId() &&
                promotion.getAvionSiege().getTypeSiege().equals(volDetails.getSiege().getTypeSiege()) &&
                promotion.getQuantite() > 0 &&
                (maintenant.isEqual(promotion.getDatePromotion()) || maintenant.isAfter(promotion.getDatePromotion())) &&
                (maintenant.isEqual(promotion.getDateExpiration()) || maintenant.isBefore(promotion.getDateExpiration()));
    }
   
    public List<Promotion> getPromotionsValidesParDate(Vol vol, TypeSiege typeSiege, LocalDate date) {
        List<Promotion> promotions = new ArrayList<>();
        // Requête avec jointure pour récupérer les infos d'avion_detailler et type_siege
        String query = "SELECT p.*, a.id AS avion_id, a.nombre_siege, ts.id AS type_siege_id, ts.type_nom " +
               "FROM promotion p " +
               "JOIN avion_detailler a ON p.id_avion_siege = a.id " +
               "JOIN type_siege ts ON a.id_type_siege = ts.id " +
               "WHERE p.id_vol = ? AND a.id_type_siege = ? AND p.date_promotion <= ? AND p.date_expiration >= ?";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vol.getId());
            stmt.setInt(2, typeSiege.getId());
            stmt.setDate(3, Date.valueOf(date));
            stmt.setDate(4, Date.valueOf(date));


            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Construire AvionDetailler avec TypeSiege
                AvionDetailler avion = new AvionDetailler();
                avion.setId(rs.getInt("avion_id"));
                avion.setNombre_siege(rs.getInt("nombre_siege"));

                TypeSiege ts = new TypeSiege();
                ts.setId(rs.getInt("type_siege_id"));
                ts.setTypeNom(rs.getString("type_nom"));
                avion.setTypeSiege(ts);

                // Construire la promotion complète
                Promotion promotion = new Promotion(
                        rs.getInt("id"),
                        rs.getDate("date_promotion").toLocalDate(),
                        rs.getDate("date_expiration").toLocalDate(),
                        vol,
                        avion,
                        rs.getInt("quantite"),
                        rs.getBigDecimal("pourcentage")
                );
                promotions.add(promotion);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return promotions;
    }

}
