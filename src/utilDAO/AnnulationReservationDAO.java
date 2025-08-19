package utilDAO;

import connex.Connexion;
import models.DemandeReservation;
import models.Vol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AnnulationReservationDAO {
       private static final long DELAI_MINIMUM_HEURES = 24; // délai minimal avant départ pour annulation

    /**
     * Remplit le vol d'une demande avec la date de départ correspondant à l'id de la demande.
     *
     * @param demande objet DemandeReservation existant avec id renseigné
     * @return DemandeReservation avec le vol mis à jour, ou null si non trouvé
     */
    public DemandeReservation getReservationWithDateDepart(DemandeReservation demande) {
        if (demande == null || demande.getId() <= 0) {
            throw new IllegalArgumentException("La demande ne peut pas être nulle et doit avoir un ID valide.");
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT v.date_depart " +
                     "FROM demande_reservation dr " +
                     "JOIN vol v ON dr.id_vol = v.id " +
                     "WHERE dr.id = ?";

        try {
            conn = Connexion.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, demande.getId());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                Vol vol = new Vol();
                if (rs.getTimestamp("date_depart") != null) {
                    vol.setDateDepart(rs.getTimestamp("date_depart").toLocalDateTime());
                }
                demande.setVol(vol);
            } else {
                demande = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            demande = null;
        } finally {
            Connexion.closeResources(rs, pstmt, conn);
        }

        return demande;
    }

    public boolean estDejaAnnulee(DemandeReservation demande) {
            if (demande == null || demande.getId() <= 0) {
                throw new IllegalArgumentException("La demande ne peut pas être nulle et doit avoir un ID valide.");
            }

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            String sql = "SELECT COUNT(*) AS nb FROM annulation_reservation WHERE id_demande = ?";

            try {
                conn = Connexion.getConnection();
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, demande.getId());

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("nb") > 0;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Connexion.closeResources(rs, pstmt, conn);
            }

            return false;
        }

    /**
     * Vérifie si la réservation peut être annulée selon le délai autorisé.
     * Exemple : délai minimal = 24 heures avant le départ
     *
     * @param demande La demande à vérifier
     * @param delaiEnHeures délai minimal avant le départ
     * @return true si le délai est respecté, false sinon
     */
    public boolean delaiAnnulationRespecte(DemandeReservation demande, long delaiEnHeures) {
        if (demande == null || demande.getVol() == null || demande.getVol().getDateDepart() == null) {
            throw new IllegalArgumentException("La demande et la date de départ du vol doivent être renseignées.");
        }

        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime dateDepart = demande.getVol().getDateDepart();

        long heuresRestantes = ChronoUnit.HOURS.between(maintenant, dateDepart);

        return heuresRestantes >= delaiEnHeures;
    }

    
    /**
     * Insère une annulation pour une demande donnée.
     *
     * @param idDemande ID de la réservation
     * @param motif Motif de l'annulation
     * @return true si l'insertion a réussi, false sinon
     */
    public boolean insertAnnulation(int idDemande, String motif) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO annulation_reservation (id_demande, date_annulation, motif) VALUES (?, ?, ?)";

        try {
            conn = Connexion.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idDemande);
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(3, motif);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Connexion.closeResources(null, pstmt, conn);
        }
    }

    /**
     * Met à jour le statut d'une demande de réservation.
     *
     * @param idDemande ID de la réservation
     * @param statut Nouveau statut (ex : "ANNULEE")
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateStatut(int idDemande, String statut) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "UPDATE demande_reservation SET statut = ? WHERE id = ?";

        try {
            conn = Connexion.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, statut);
            pstmt.setInt(2, idDemande);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Connexion.closeResources(null, pstmt, conn);
        }
    }

     /**
     * Annule une réservation en appliquant toutes les règles :
     *  - vérifie que la réservation existe
     *  - vérifie qu'elle n'a pas déjà été annulée
     *  - vérifie le délai minimal avant départ
     *  - insère l'annulation et met à jour le statut
     *
     * @param demande La demande à annuler
     * @param motif Motif de l'annulation
     * @return true si l'annulation a réussi, false sinon
     */
    public boolean annulerReservation(DemandeReservation demande, String motif) {
        if (demande == null || demande.getId() <= 0) {
            throw new IllegalArgumentException("La demande ne peut pas être nulle et doit avoir un ID valide.");
        }

        // Récupérer la date de départ du vol
        demande = getReservationWithDateDepart(demande);
        if (demande == null || demande.getVol() == null || demande.getVol().getDateDepart() == null) {
            System.out.println("Impossible de récupérer la réservation ou la date de départ du vol.");
            return false;
        }

        // Vérifier si déjà annulée
        if (estDejaAnnulee(demande)) {
            System.out.println("La réservation a déjà été annulée.");
            return false;
        }

        // Vérifier le délai d'annulation
        if (!delaiAnnulationRespecte(demande, DELAI_MINIMUM_HEURES)) {
            System.out.println("Le délai minimal avant le départ n'est pas respecté.");
            return false;
        }

        // Insérer l'annulation
        boolean insertOk = insertAnnulation(demande.getId(), motif);
        if (!insertOk) {
            System.out.println("Erreur lors de l'insertion de l'annulation.");
            return false;
        }

        // Mettre à jour le statut de la réservation
        boolean updateOk = updateStatut(demande.getId(), "ANNULEE");
        if (!updateOk) {
            System.out.println("Erreur lors de la mise à jour du statut.");
            return false;
        }

        System.out.println("Réservation annulée avec succès.");
        return true;
    }
}
