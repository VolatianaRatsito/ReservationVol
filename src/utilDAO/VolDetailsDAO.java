package utilDAO;

import models.Vol;
import models.Avion;
import models.AvionDetailler;
import models.VolDetails;
import models.Categorie;
import models.Tarif;
import models.TypeSiege;
import utilDAO.VolDAO;
import connex.Connexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolDetailsDAO {

    // Méthode pour insérer un VolDetails avec le tarif récupéré en fonction de la catégorie et de la date actuelle
    public VolDetails insererVolDetails(Vol vol, int nombreDisponible, AvionDetailler siege, Categorie categorie) throws SQLException, ClassNotFoundException {
        int id = -1;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tarif tarif = null;

        try {
            conn = Connexion.getConnection();
            conn.setAutoCommit(false);

            // Récupérer le tarif en fonction de la catégorie
            TarifDAO tarifDAO = new TarifDAO();
            tarif = tarifDAO.getCurrentTarifByCategorie(categorie); // Méthode modifiée

            if (tarif == null) {
                throw new SQLException("Aucun tarif trouvé pour la catégorie : " + categorie.getNom());
            }

            // Requête SQL pour insérer un VolDetails
            String query = "INSERT INTO vol_details (idVol, nombre_disponible, idSiege, idTarif) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, vol.getId());
            stmt.setInt(2, nombreDisponible);
            stmt.setInt(3, siege.getId());
            stmt.setInt(4, tarif.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return id != -1 ? new VolDetails(id, vol, nombreDisponible, siege, tarif) : null;
    }

    // Méthode pour récupérer un VolDetails en fonction du vol, du siège et de la catégorie
    public VolDetails getVolDetails(Vol vol, AvionDetailler siege, Categorie categorie) {
        String sql = "SELECT vd.id, vd.nombre_disponible, vd.idTarif " +
                    "FROM vol_details vd " +
                    "JOIN tarif t ON vd.idTarif = t.id " +
                    "WHERE vd.idVol = ? AND vd.idSiege = ? AND t.idCategorie = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, vol.getId());
            stmt.setInt(2, siege.getId());
            stmt.setInt(3, categorie.getId());

            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int nombreDisponible = rs.getInt("nombre_disponible");
                int idTarif = rs.getInt("idTarif");

                // Récupérer le tarif
                TarifDAO tarifDAO = new TarifDAO();
                Tarif tarif = tarifDAO.getTarifById(idTarif);

                return new VolDetails(id, vol, nombreDisponible, siege, tarif);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return null;
    }

    public VolDetails getById(int idVolDetails) {
        String sql = """
            SELECT vd.id, vd.idVol, vd.nombre_disponible, vd.idSiege, vd.idTarif,
                v.nom AS nom_vol,
                t.montant, 
                c.id AS cat_id, c.nom AS cat_nom, c.age_min, c.age_max,
                ad.id AS siege_id, ad.nombre_siege, 
                ts.id AS type_siege_id, ts.type_nom
            FROM vol_details vd
            JOIN vol v ON vd.idVol = v.id
            JOIN tarif t ON vd.idTarif = t.id
            JOIN categorie c ON t.idCategorie = c.id
            JOIN avion_detailler ad ON vd.idSiege = ad.id
            JOIN type_siege ts ON ad.idSiege = ts.id
            WHERE vd.id = ?
        """;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idVolDetails);

            rs = stmt.executeQuery();
            if (rs.next()) {
                // Vol
                Vol vol = new Vol();
                vol.setId(rs.getInt("idVol"));
                vol.setNom(rs.getString("nom_vol"));

                // TypeSiege
                TypeSiege typeSiege = new TypeSiege();
                typeSiege.setId(rs.getInt("type_siege_id"));
                typeSiege.setTypeNom(rs.getString("type_nom"));

                // AvionDetailler
                AvionDetailler siege = new AvionDetailler();
                siege.setId(rs.getInt("siege_id"));
                siege.setNombre_siege(rs.getInt("nombre_siege"));
                siege.setTypeSiege(typeSiege);

                // Categorie
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("cat_id"));
                categorie.setNom(rs.getString("cat_nom"));
                categorie.setAgeMin(rs.getInt("age_min"));
                categorie.setAgeMax(rs.getInt("age_max"));

                // Tarif
                Tarif tarif = new Tarif();
                tarif.setId(rs.getInt("idTarif"));
                tarif.setMontant(rs.getBigDecimal("montant"));
                tarif.setCategorie(categorie);

                // VolDetails
                VolDetails volDetails = new VolDetails();
                volDetails.setId(rs.getInt("id"));
                volDetails.setVol(vol);
                volDetails.setNombreDisponible(rs.getInt("nombre_disponible"));
                volDetails.setSiege(siege);
                volDetails.setTarif(tarif);

                return volDetails;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }

        return null;
    }


    public List<VolDetails> getDetailsVolParClasseEtCategorie(int volId) {
    List<VolDetails> resultats = new ArrayList<>();
    String sql = "SELECT vd.id, vd.nombre_disponible, "
               + "ad.id AS id_avion_detailler, ad.nombre_siege, "
               + "ts.id AS id_type_siege, ts.type_nom, "
               + "t.id AS id_tarif, t.montant, "
               + "cat.id AS id_categorie, cat.nom AS nom_categorie "
               + "FROM vol_details vd "
               + "JOIN vol v ON vd.idVol = v.id "
               + "JOIN avion_detailler ad ON vd.idSiege = ad.id "
               + "JOIN type_siege ts ON ad.idSiege = ts.id "
               + "JOIN tarif t ON vd.idTarif = t.id "
               + "JOIN categorie cat ON t.idCategorie = cat.id "
               + "WHERE v.id = ? "
               + "ORDER BY ts.type_nom, cat.nom";

    try (Connection conn = Connexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, volId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Création des objets nécessaires
                Vol vol = new Vol();
                vol.setId(volId);
                
                TypeSiege typeSiege = new TypeSiege();
                typeSiege.setId(rs.getInt("id_type_siege"));
                typeSiege.setTypeNom(rs.getString("type_nom"));
                
                Avion avion = new Avion(); // Vous devrez peut-être récupérer plus d'infos si nécessaire
                
                AvionDetailler siege = new AvionDetailler();
                siege.setId(rs.getInt("id_avion_detailler"));
                siege.setAvion(avion);
                siege.setTypeSiege(typeSiege);
                siege.setNombre_siege(rs.getInt("nombre_siege"));
                
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("id_categorie"));
                categorie.setNom(rs.getString("nom_categorie"));
                
                Tarif tarif = new Tarif();
                tarif.setId(rs.getInt("id_tarif"));
                tarif.setMontant(rs.getBigDecimal("montant"));
                tarif.setCategorie(categorie);
                
                // Création du VolDetails
                VolDetails volDetails = new VolDetails();
                volDetails.setId(rs.getInt("id"));
                volDetails.setVol(vol);
                volDetails.setNombreDisponible(rs.getInt("nombre_disponible"));
                volDetails.setSiege(siege);
                volDetails.setTarif(tarif);
                
                resultats.add(volDetails);
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    
    return resultats;
}

    /**
     * Récupère un VolDetails spécifique en fonction de l'ID du vol et de l'ID du siège
     * @param vol 
     * @param siege 
     * @return VolDetails correspondant ou null si non trouvé
     */
   public VolDetails getVolDetailsByVolAndSiege(Vol vol, AvionDetailler siege) {
        String sql = "SELECT * FROM vol_details WHERE idvol = ? AND idsiege = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, vol.getId());
            stmt.setInt(2, siege.getId());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                VolDetails volDetails = new VolDetails();
                volDetails.setId(rs.getInt("id"));
                volDetails.setVol(vol);
                volDetails.setNombreDisponible(rs.getInt("nombre_disponible"));
                
                // Initialisation de l'objet AvionDetailler
                AvionDetailler avionDetailler = new AvionDetailler();
                avionDetailler.setId(rs.getInt("idsiege"));
                volDetails.setSiege(avionDetailler);
                
                // Initialisation de l'objet Tarif
                Tarif tarif = new Tarif();
                tarif.setId(rs.getInt("idtarif"));
                volDetails.setTarif(tarif);
                
                return volDetails;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Utilisation de la méthode de fermeture centralisée
            Connexion.closeResources(rs, stmt, conn);
        }
        return null;
    }

    public List<VolDetails> getAllVolDetails() throws SQLException, ClassNotFoundException {
        List<VolDetails> volDetailsList = new ArrayList<>();

        String sql = """
            SELECT vd.id, vd.idVol, vd.nombre_disponible, vd.idSiege, vd.idTarif,
                v.nom AS nom_vol,
                t.montant,
                c.id AS cat_id, c.nom AS cat_nom, c.age_min, c.age_max,
                ad.id AS siege_id, ad.nombre_siege,
                ts.id AS type_siege_id, ts.type_nom
            FROM vol_details vd
            JOIN vol v ON vd.idVol = v.id
            JOIN tarif t ON vd.idTarif = t.id
            JOIN categorie c ON t.idCategorie = c.id
            JOIN avion_detailler ad ON vd.idSiege = ad.id
            JOIN type_siege ts ON ad.idSiege = ts.id
            ORDER BY vd.id
        """;

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Création des objets nécessaires
                Vol vol = new Vol();
                vol.setId(rs.getInt("idVol"));
                vol.setNom(rs.getString("nom_vol"));

                TypeSiege typeSiege = new TypeSiege();
                typeSiege.setId(rs.getInt("type_siege_id"));
                typeSiege.setTypeNom(rs.getString("type_nom"));

                AvionDetailler siege = new AvionDetailler();
                siege.setId(rs.getInt("siege_id"));
                siege.setNombre_siege(rs.getInt("nombre_siege"));
                siege.setTypeSiege(typeSiege);

                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("cat_id"));
                categorie.setNom(rs.getString("cat_nom"));
                categorie.setAgeMin(rs.getInt("age_min"));
                categorie.setAgeMax(rs.getInt("age_max"));

                Tarif tarif = new Tarif();
                tarif.setId(rs.getInt("idTarif"));
                tarif.setMontant(rs.getBigDecimal("montant"));
                tarif.setCategorie(categorie);

                VolDetails volDetails = new VolDetails();
                volDetails.setId(rs.getInt("id"));
                volDetails.setVol(vol);
                volDetails.setNombreDisponible(rs.getInt("nombre_disponible"));
                volDetails.setSiege(siege);
                volDetails.setTarif(tarif);

                volDetailsList.add(volDetails);
            }
        }

        return volDetailsList;
    }

    /**
     * Vérifie le nombre de places disponibles pour un vol, un siège et une catégorie
     * On prend les objets Vol, AvionDetailler et Categorie pour vérifier la disponibilité des sièges
     * @return le nombre de places disponibles, ou -1 si aucune donnée n’est trouvée
     */
    public int getNombrePlacesDisponibles(Vol vol, VolDetails volDetails, Categorie categorie) {
        String sql = """
            SELECT vd.nombre_disponible
            FROM vol_details vd
            JOIN tarif t ON vd.idTarif = t.id
            WHERE vd.idVol = ? AND vd.idSiege = ? AND t.idCategorie = ?
        """;

        try (
            Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Extraction des IDs depuis les objets passés en paramètres
            stmt.setInt(1, vol.getId());
            stmt.setInt(2, volDetails.getId());
            stmt.setInt(3, categorie.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("nombre_disponible");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Tu peux aussi logger proprement si besoin
        }

        return -1; // Aucun résultat trouvé
    }

    public List<VolDetails> getVolDetailsByVol(Vol vol) {
        List<VolDetails> volDetailsList = new ArrayList<>();

        String sql = """
            SELECT vd.id, vd.nombre_disponible,
                ad.id AS siege_id, ad.nombre_siege,
                ts.id AS type_siege_id, ts.type_nom,
                t.id AS tarif_id, t.montant,
                c.id AS cat_id, c.nom AS cat_nom, c.age_min, c.age_max
            FROM vol_details vd
            JOIN avion_detailler ad ON vd.idsiege = ad.id
            JOIN type_siege ts ON ad.id_type_siege = ts.id
            JOIN tarif t ON vd.idtarif = t.id
            JOIN categorie c ON t.id_categorie = c.id
            WHERE vd.idvol = ?
            ORDER BY ts.type_nom, c.nom
        """;

        try (Connection conn = Connexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, vol.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // TypeSiege
                    TypeSiege typeSiege = new TypeSiege();
                    typeSiege.setId(rs.getInt("type_siege_id"));
                    typeSiege.setTypeNom(rs.getString("type_nom"));

                    // AvionDetailler
                    AvionDetailler siege = new AvionDetailler();
                    siege.setId(rs.getInt("siege_id"));
                    siege.setNombre_siege(rs.getInt("nombre_siege"));
                    siege.setTypeSiege(typeSiege);

                    // Categorie
                    Categorie categorie = new Categorie();
                    categorie.setId(rs.getInt("cat_id"));
                    categorie.setNom(rs.getString("cat_nom"));
                    categorie.setAgeMin(rs.getInt("age_min"));
                    categorie.setAgeMax(rs.getInt("age_max"));

                    // Tarif
                    Tarif tarif = new Tarif();
                    tarif.setId(rs.getInt("tarif_id"));
                    tarif.setMontant(rs.getBigDecimal("montant"));
                    tarif.setCategorie(categorie);

                    // VolDetails
                    VolDetails volDetails = new VolDetails();
                    volDetails.setId(rs.getInt("id"));
                    volDetails.setVol(vol);
                    volDetails.setNombreDisponible(rs.getInt("nombre_disponible"));
                    volDetails.setSiege(siege);
                    volDetails.setTarif(tarif);

                    volDetailsList.add(volDetails);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return volDetailsList;
    }

    public static void decreaseNombrePlaces(Vol vol, List<VolDetails> volDetailsList, int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être strictement positive.");
        }

        Connection conn = null;

        try {
            conn = Connexion.getConnection();
            conn.setAutoCommit(false);

            String updateSql = "UPDATE vol_details SET nombre_disponible = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                int reste = quantite;

                for (VolDetails vd : volDetailsList) {
                    int dispo = vd.getNombreDisponible();

                    if (dispo >= reste) {
                        int nouvelleDisponibilite = dispo - reste;
                        stmt.setInt(1, nouvelleDisponibilite);
                        stmt.setInt(2, vd.getId());
                        stmt.executeUpdate();

                        vd.setNombreDisponible(nouvelleDisponibilite);

                        reste = 0;
                        break;
                    } else if (dispo > 0) {
                        stmt.setInt(1, 0);
                        stmt.setInt(2, vd.getId());
                        stmt.executeUpdate();

                        vd.setNombreDisponible(0);

                        reste -= dispo;
                    }
                }

                if (reste > 0) {
                    conn.rollback();
                    throw new IllegalStateException("Quantité demandée trop élevée : " + quantite + ", disponible : " + (quantite - reste));
                }

                conn.commit();
            }
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Erreur lors de la mise à jour des places disponibles", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


     public boolean updateVolDetails(VolDetails volDetails) {
        String sql = "UPDATE vol_details SET nombre_disponible = ? WHERE id = ?";
        
        try (Connection conn = Connexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, volDetails.getNombreDisponible());
            stmt.setInt(2, volDetails.getId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}