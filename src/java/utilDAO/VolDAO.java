package utilDAO;

import models.Avion;
import models.AvionDetailler;
import models.VilleDesservie;
import models.Vol;
import models.VolDetails;
import connex.Connexion;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VolDAO {

    private boolean verifierInformations(Vol vol) {
        if (vol.getVilleDepart().equals(vol.getVilleArriver())) {
            System.out.println("Erreur : La ville de départ et d'arrivée ne peuvent pas être identiques.");
            return false;
        }
        if (vol.getDateDepart().isAfter(vol.getDateArriver())) {
            System.out.println("Erreur : La date de départ doit être avant la date d'arrivée.");
            return false;
        }
        return true;
    }

    //  Vérifie si l'avion est disponible pour le vol.
    private boolean verifierDisponibiliteAvion(Vol vol) {
        String sql = "SELECT COUNT(*) FROM vol WHERE idAvion = ? AND " +
                     "(date_depart BETWEEN ? AND ? OR date_arriver BETWEEN ? AND ? OR " +
                     "(date_depart <= ? AND date_arriver >= ?))";
    
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql);
    
            stmt.setInt(1, vol.getAvion().getId());
            stmt.setTimestamp(2, Timestamp.valueOf(vol.getDateDepart()));
            stmt.setTimestamp(3, Timestamp.valueOf(vol.getDateArriver()));
            stmt.setTimestamp(4, Timestamp.valueOf(vol.getDateDepart()));
            stmt.setTimestamp(5, Timestamp.valueOf(vol.getDateArriver()));
            stmt.setTimestamp(6, Timestamp.valueOf(vol.getDateDepart()));
            stmt.setTimestamp(7, Timestamp.valueOf(vol.getDateArriver()));
    
            rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Erreur : L'avion est déjà programmé pour un autre vol à cette période.");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn); 
        }
        return true;
    }
    
    public List<Vol> getAllVols() {
        List<Vol> vols = new ArrayList<>();
        String query = "SELECT v.id, v.nom, v.idAvion, a.nom AS avion_nom, a.date_fabrication, " +
                       "vd1.id AS ville_depart_id, vd1.ville AS ville_depart, " +
                       "vd2.id AS ville_arriver_id, vd2.ville AS ville_arriver, " +
                       "v.date_depart, v.date_arriver " +
                       "FROM vol v " +
                       "JOIN avion a ON v.idAvion = a.id " +
                       "JOIN ville_desservie vd1 ON v.ville_depart = vd1.id " +
                       "JOIN ville_desservie vd2 ON v.ville_arriver = vd2.id";
        
        try (Connection conn = Connexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                
                Avion avion = new Avion(rs.getInt("idAvion"), rs.getString("avion_nom"), null, rs.getTimestamp("date_fabrication").toLocalDateTime());
                
                VilleDesservie villeDepart = new VilleDesservie(rs.getInt("ville_depart_id"), rs.getString("ville_depart"));
                VilleDesservie villeArriver = new VilleDesservie(rs.getInt("ville_arriver_id"), rs.getString("ville_arriver"));
                
                LocalDateTime dateDepart = rs.getTimestamp("date_depart").toLocalDateTime();
                LocalDateTime dateArriver = rs.getTimestamp("date_arriver").toLocalDateTime();
                
                Vol vol = new Vol(id, nom, avion, villeDepart, villeArriver, dateDepart, dateArriver);
                vols.add(vol);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return vols;
    }

    public List<Vol> searchVolsByCriteria(VilleDesservie ville, LocalDate dateDebut, LocalDate dateFin, Avion avion) {
        List<Vol> vols = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT v.id AS vol_id, v.nom AS vol_nom, v_details.nombre_disponible, " +
            "av.id AS IdAvion, av.nom AS avion_nom, ville_depart.ville AS ville_depart, " +
            "ville_arriver.ville AS ville_arriver, v.date_depart, v.date_arriver, " +
            "v_details.montant, ts.type_nom AS type_siege " +
            "FROM vol v " +
            "JOIN ville_desservie ville_depart ON v.ville_depart = ville_depart.id " +
            "JOIN ville_desservie ville_arriver ON v.ville_arriver = ville_arriver.id " +
            "JOIN avion av ON v.idAvion = av.id " +
            "JOIN vol_details v_details ON v.id = v_details.idVol " +
            "JOIN avion_detailler av_detailler ON av.id = av_detailler.idAvion " +
            "JOIN type_siege ts ON av_detailler.idSiege = ts.id " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        // Filtrer par ville (départ ou arrivée)
        if (ville != null && ville.getVille() != null && !ville.getVille().isEmpty()) {
            sql.append("AND (ville_depart.ville = ? OR ville_arriver.ville = ?) ");
            params.add(ville.getVille());
            params.add(ville.getVille());
        }

        // Filtrer par plage de dates
        if (dateDebut != null && dateFin != null) {
            LocalDateTime startDateTime = dateDebut.atStartOfDay(); // Début du jour
            LocalDateTime endDateTime = dateFin.atTime(23, 59, 59); // Fin du jour
            sql.append("AND v.date_depart BETWEEN ? AND ? ");
            params.add(Timestamp.valueOf(startDateTime));
            params.add(Timestamp.valueOf(endDateTime));
        }

        // Filtrer par le nom de l'avion
        if (avion != null && avion.getNom() != null && !avion.getNom().isEmpty()) {
            sql.append("AND av.nom = ? ");
            params.add(avion.getNom());
        }

        // Trier par date de départ
        sql.append("ORDER BY v.date_depart");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Connexion.getConnection();
            stmt = conn.prepareStatement(sql.toString());

            // Attribution dynamique des paramètres
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                int volId = rs.getInt("vol_id");
                String volNom = rs.getString("vol_nom");
                int nombreDisponible = rs.getInt("nombre_disponible");
                int avionId = rs.getInt("IdAvion");
                String avionNom = rs.getString("avion_nom");
                String villeDepartStr = rs.getString("ville_depart");
                String villeArriverStr = rs.getString("ville_arriver");
                LocalDateTime dateDepart = rs.getTimestamp("date_depart").toLocalDateTime();
                LocalDateTime dateArriver = rs.getTimestamp("date_arriver").toLocalDateTime();
                BigDecimal montant = rs.getBigDecimal("montant");
                String siege = rs.getString("type_siege");

                // Création des objets correspondant
                Avion avionObj = new Avion(avionId, avionNom, null, null);
                VilleDesservie villeDepartObj = new VilleDesservie(0, villeDepartStr);
                VilleDesservie villeArriverObj = new VilleDesservie(0, villeArriverStr);

                // Construction de l'objet Vol à partir des données récupérées
                Vol vol = new Vol(volId, volNom, avionObj, villeDepartObj, villeArriverObj, dateDepart, dateArriver);
                vols.add(vol);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Connexion.closeResources(rs, stmt, conn);
        }
        return vols;
    }
    
    public boolean insertVol(Vol vol) {
    // Vérifier les informations du vol
    if (!verifierInformations(vol)) {
        return false; // Les informations sont invalides
    }

    // Vérifier la disponibilité de l'avion
    if (!verifierDisponibiliteAvion(vol)) {
        return false; // L'avion n'est pas disponible
    }

    String sql = "INSERT INTO vol (nom, idAvion, ville_depart, ville_arriver, date_depart, date_arriver) VALUES (?, ?, ?, ?, ?, ?)";

    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = Connexion.getConnection();
        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, vol.getNom());
        stmt.setInt(2, vol.getAvion().getId());
        stmt.setInt(3, vol.getVilleDepart().getId());
        stmt.setInt(4, vol.getVilleArriver().getId());
        stmt.setTimestamp(5, Timestamp.valueOf(vol.getDateDepart()));
        stmt.setTimestamp(6, Timestamp.valueOf(vol.getDateArriver()));

        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Échec de l'insertion du vol, aucune ligne affectée.");
        }

        // Optionnel : Récupérer l'ID du vol inséré
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                vol.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Échec de l'insertion du vol, aucun ID généré.");
            }
        }

        return true; // Insertion réussie
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        return false; // Une erreur est survenue
    } finally {
        Connexion.closeResources(null, stmt, conn);
    }
}
   
}
