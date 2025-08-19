package utilDAO;

import models.Categorie;
import models.Tarif;
import models.Vol;
import models.AvionDetailler;
import connex.Connexion;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TarifDAO {

    // Récupérer un tarif par ID complet
    public Tarif getTarifById(int idTarif) {
        String sql = "SELECT t.id, t.id_categorie, t.montant, t.date_debut, t.date_fin, t.id_vol, t.id_avion_siege, t.created_at, " +
                     "c.nom AS cat_nom, c.age_min, c.age_max, " +
                     "v.nom AS vol_nom, v.idavion, v.ville_depart, v.ville_arriver, v.date_depart, v.date_arriver, " +
                     "ad.id AS avion_det_id, ad.nombre_siege " +
                     "FROM tarif t " +
                     "JOIN categorie c ON t.id_categorie = c.id " +
                     "LEFT JOIN vol v ON t.id_vol = v.id " +
                     "LEFT JOIN avion_detailler ad ON t.id_avion_siege = ad.id " +
                     "WHERE t.id = ?";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarif);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Construire Categorie
                    Categorie categorie = new Categorie(
                            rs.getInt("id_categorie"),
                            rs.getString("cat_nom"),
                            rs.getInt("age_min"),
                            rs.getObject("age_max") != null ? rs.getInt("age_max") : null
                    );

                    // Construire Vol (peu complet, tu peux adapter)
                    Vol vol = null;
                    if (rs.getObject("id_vol") != null) {
                        vol = new Vol();
                        vol.setId(rs.getInt("id_vol"));
                        vol.setNom(rs.getString("vol_nom"));
                        // tu peux récupérer plus de champs comme Avion, villes etc. si besoin
                    }

                    // Construire AvionDetailler minimal
                    AvionDetailler avionDetailler = null;
                    if (rs.getObject("id_avion_siege") != null) {
                        avionDetailler = new AvionDetailler();
                        avionDetailler.setId(rs.getInt("id_avion_siege"));
                        avionDetailler.setNombre_siege(rs.getInt("nombre_siege"));
                        // Pour plus de détails, il faudrait récupérer l'avion et le type siege ici
                    }

                    return new Tarif(
                            rs.getInt("id"),
                            categorie,
                            rs.getBigDecimal("montant"),
                            rs.getDate("date_debut") != null ? rs.getDate("date_debut").toLocalDate() : null,
                            rs.getDate("date_fin") != null ? rs.getDate("date_fin").toLocalDate() : null,
                            vol,
                            avionDetailler,
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Récupérer tous les tarifs
    public List<Tarif> getAllTarifs() {
        List<Tarif> tarifs = new ArrayList<>();
        String sql = "SELECT id FROM tarif";

        try (Connection conn = Connexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tarif tarif = getTarifById(rs.getInt("id"));
                if (tarif != null) {
                    tarifs.add(tarif);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tarifs;
    }

    // Méthode d'insertion simple (adapter selon besoin)
    public void insertTarif(Tarif tarif) {
        String sql = "INSERT INTO tarif (id_categorie, montant, date_debut, date_fin, id_vol, id_avion_siege) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, tarif.getCategorie().getId());
            stmt.setBigDecimal(2, tarif.getMontant());
            if (tarif.getDateDebut() != null) {
                stmt.setDate(3, Date.valueOf(tarif.getDateDebut()));
            } else {
                stmt.setNull(3, Types.DATE);
            }
            if (tarif.getDateFin() != null) {
                stmt.setDate(4, Date.valueOf(tarif.getDateFin()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            if (tarif.getVol() != null) {
                stmt.setInt(5, tarif.getVol().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            if (tarif.getAvionDetailler() != null) {
                stmt.setInt(6, tarif.getAvionDetailler().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tarif.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Tarif> getTarifByCategorie(Categorie categorie) {
        List<Tarif> tarifs = new ArrayList<>();

        String sql = "SELECT t.id, t.id_categorie, t.montant, t.date_debut, t.date_fin, t.id_vol, t.id_avion_siege, t.created_at, " +
                    "c.nom AS cat_nom, c.age_min, c.age_max, " +
                    "v.nom AS vol_nom, v.idavion, v.ville_depart, v.ville_arriver, v.date_depart, v.date_arriver, " +
                    "ad.id AS avion_det_id, ad.nombre_siege " +
                    "FROM tarif t " +
                    "JOIN categorie c ON t.id_categorie = c.id " +
                    "LEFT JOIN vol v ON t.id_vol = v.id " +
                    "LEFT JOIN avion_detailler ad ON t.id_avion_siege = ad.id " +
                    "WHERE t.id_categorie = ?";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Utiliser l'ID de l'objet Categorie passé en paramètre
            stmt.setInt(1, categorie.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Construire un nouvel objet Categorie pour chaque Tarif retourné
                    // (car info complète peut être différente, ou pour éviter aliasing)
                    Categorie cat = new Categorie(
                            rs.getInt("id_categorie"),
                            rs.getString("cat_nom"),
                            rs.getInt("age_min"),
                            rs.getObject("age_max") != null ? rs.getInt("age_max") : null
                    );

                    Vol vol = null;
                    if (rs.getObject("id_vol") != null) {
                        vol = new Vol();
                        vol.setId(rs.getInt("id_vol"));
                        vol.setNom(rs.getString("vol_nom"));
                    }

                    AvionDetailler avionDetailler = null;
                    if (rs.getObject("id_avion_siege") != null) {
                        avionDetailler = new AvionDetailler();
                        avionDetailler.setId(rs.getInt("id_avion_siege"));
                        avionDetailler.setNombre_siege(rs.getInt("nombre_siege"));
                    }

                    Tarif tarif = new Tarif(
                            rs.getInt("id"),
                            cat,
                            rs.getBigDecimal("montant"),
                            rs.getDate("date_debut") != null ? rs.getDate("date_debut").toLocalDate() : null,
                            rs.getDate("date_fin") != null ? rs.getDate("date_fin").toLocalDate() : null,
                            vol,
                            avionDetailler,
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                    );

                    tarifs.add(tarif);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return tarifs;
    }

    public Tarif getCurrentTarifByCategorie(Categorie categorie) {
        LocalDate today = LocalDate.now();
        String sql = "SELECT id FROM tarif " +
                    "WHERE id_categorie = ? " +
                    "AND (date_debut IS NULL OR date_debut <= ?) " +
                    "AND (date_fin IS NULL OR date_fin >= ?) " +
                    "ORDER BY created_at DESC " +
                    "LIMIT 1";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categorie.getId());
            stmt.setDate(2, Date.valueOf(today));
            stmt.setDate(3, Date.valueOf(today));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getTarifById(rs.getInt("id"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
