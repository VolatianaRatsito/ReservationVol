package utilDAO;

import models.AvionDetailler;
import models.Avion;
import models.Modele;
import models.TypeSiege;
import models.Vol;
import connex.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvionDetailsDAO {

    public List<AvionDetailler> getAllAvionDetailler() {
        List<AvionDetailler> result = new ArrayList<>();
        String sql = """
            SELECT ad.id, ad.id_avion, ad.id_type_siege, ad.nombre_siege,
                   t.type_nom AS siegeType,
                   a.nom AS avionNom, a.idmodele, a.date_fabrication,
                   a.immatriculation, a.created_at
            FROM avion_detailler ad
            JOIN avion a ON ad.id_avion = a.id
            JOIN type_siege t ON ad.id_type_siege = t.id
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Modele modele = new ModeleDAO().getModeleById(rs.getInt("idmodele"));

                java.time.LocalDate dateFabrication = null;
                if (rs.getDate("date_fabrication") != null) {
                    dateFabrication = rs.getDate("date_fabrication").toLocalDate();
                }

                java.time.LocalDateTime createdAt = null;
                if (rs.getTimestamp("created_at") != null) {
                    createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                }

                Avion avion = new Avion(
                        rs.getInt("id_avion"),
                        rs.getString("avionNom"),
                        modele,
                        dateFabrication,
                        rs.getString("immatriculation"),
                        createdAt
                );

                TypeSiege typeSiege = new TypeSiege();
                typeSiege.setId(rs.getInt("id_type_siege"));
                typeSiege.setTypeNom(rs.getString("siegeType"));

                AvionDetailler avionDetailler = new AvionDetailler(
                        rs.getInt("id"),
                        avion,
                        typeSiege,
                        rs.getInt("nombre_siege")
                );

                result.add(avionDetailler);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public AvionDetailler getAvionDetaillerByAvionAndSiege(int idAvion, int idSiege) {
        String sql = """
            SELECT ad.id, ad.id_avion, ad.id_type_siege, ad.nombre_siege,
                   t.type_nom AS siegeType,
                   a.nom AS avionNom, a.idmodele, a.date_fabrication,
                   a.immatriculation, a.created_at
            FROM avion_detailler ad
            JOIN avion a ON ad.id_avion = a.id
            JOIN type_siege t ON ad.id_type_siege = t.id
            WHERE ad.id_avion = ? AND ad.id_type_siege = ?
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAvion);
            stmt.setInt(2, idSiege);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Modele modele = new ModeleDAO().getModeleById(rs.getInt("idmodele"));

                    Avion avion = new Avion(
                            idAvion,
                            rs.getString("avionNom"),
                            modele,
                            rs.getDate("date_fabrication") != null ? rs.getDate("date_fabrication").toLocalDate() : null,
                            rs.getString("immatriculation"),
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                    );

                    TypeSiege typeSiege = new TypeSiege();
                    typeSiege.setId(idSiege);
                    typeSiege.setTypeNom(rs.getString("siegeType"));

                    return new AvionDetailler(
                            rs.getInt("id"),
                            avion,
                            typeSiege,
                            rs.getInt("nombre_siege")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AvionDetailler getByVolAndType(Vol vol, TypeSiege typeSiege) {
        if (vol == null || typeSiege == null || vol.getAvion() == null) {
            return null;
        }

        int idAvion = vol.getAvion().getId();
        int idTypeSiege = typeSiege.getId();

        String sql = """
            SELECT ad.id, ad.id_avion, ad.id_type_siege, ad.nombre_siege,
                   t.type_nom AS siegeType,
                   a.nom AS avionNom, a.idmodele, a.date_fabrication,
                   a.immatriculation, a.created_at
            FROM avion_detailler ad
            JOIN avion a ON ad.id_avion = a.id
            JOIN type_siege t ON ad.id_type_siege = t.id
            WHERE ad.id_avion= ? AND ad.id_type_siege = ?
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAvion);
            stmt.setInt(2, idTypeSiege);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Modele modele = new ModeleDAO().getModeleById(rs.getInt("idmodele"));

                    Avion avion = new Avion(
                            idAvion,
                            rs.getString("avionNom"),
                            modele,
                            rs.getDate("date_fabrication") != null ? rs.getDate("date_fabrication").toLocalDate() : null,
                            rs.getString("immatriculation"),
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                    );

                    TypeSiege ts = new TypeSiege(
                            idTypeSiege,
                            rs.getString("siegeType")
                    );

                    return new AvionDetailler(
                            rs.getInt("id"),
                            avion,
                            ts,
                            rs.getInt("nombre_siege")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AvionDetailler getById(int id) {
        String sql = """
            SELECT ad.id, ad.id_avion, ad.id_type_siege, ad.nombre_siege,
                   t.type_nom AS siegeType,
                   a.nom AS avionNom, a.idmodele, a.date_fabrication,
                   a.immatriculation, a.created_at
            FROM avion_detailler ad
            JOIN avion a ON ad.id_avion = a.id
            JOIN type_siege t ON ad.id_type_siege = t.id
            WHERE ad.id = ?
        """;

        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Modele modele = new ModeleDAO().getModeleById(rs.getInt("idmodele"));

                    Avion avion = new Avion(
                            rs.getInt("id_avion"),
                            rs.getString("avionNom"),
                            modele,
                            rs.getDate("date_fabrication") != null ? rs.getDate("date_fabrication").toLocalDate() : null,
                            rs.getString("immatriculation"),
                            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
                    );

                    TypeSiege ts = new TypeSiege(
                            rs.getInt("id_type_siege"),
                            rs.getString("siegeType")
                    );

                    return new AvionDetailler(
                            rs.getInt("id"),
                            avion,
                            ts,
                            rs.getInt("nombre_siege")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
