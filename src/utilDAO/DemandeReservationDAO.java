package utilDAO;

import connex.Connexion;
import models.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemandeReservationDAO {

    private PassportDAO passportDAO = new PassportDAO();
    private VolDetailsDAO volDetailsDAO = new VolDetailsDAO();
    private PromotionDAO promotionDAO = new PromotionDAO();
    private TarifDAO tarifDAO = new TarifDAO();

    public boolean insertReservation(DemandeReservation ligne) {
        String sql = "INSERT INTO demande_reservation " +
                    "(id_passager, id_vol, id_avion_siege, id_tarif, quantite, id_mode_paiement, date_demande, montant_total) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ligne.getPassager().getId());
            pstmt.setInt(2, ligne.getVol().getId());
            pstmt.setInt(3, ligne.getAvionDetailler().getId()); // corrigé ici
            pstmt.setInt(4, ligne.getTarif().getId()); // tarif direct, pas catégorie
            pstmt.setInt(5, ligne.getQuantite());
            pstmt.setInt(6, ligne.getModePaiement().getId());
            pstmt.setTimestamp(7, Timestamp.valueOf(ligne.getDateDemande()));
            pstmt.setBigDecimal(8, ligne.getMontantTotal());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Faire une réservation multiple (plusieurs lignes)
     * Chaque ligne correspond à un type de siège et catégorie différente.
     */
  public boolean faireReservationMultiple(Passager passager, Vol vol, List<DemandeReservation> lignesReservation, LocalDate dateReservation)
        throws SQLException, ClassNotFoundException {

        // 1. Vérifier le passeport
        Passport passportValide = passportDAO.getPassportsByPassenger(passager.getId())
                .stream()
                .filter(p -> passportDAO.isPassportValidePourVol(p, vol, true))
                .findFirst()
                .orElse(null);

        if (passportValide == null) {
            System.out.println("Passeport invalide ou absent");
            return false;
        }

        // 2. Récupérer tous les détails du vol
        List<VolDetails> volDetailsList = volDetailsDAO.getVolDetailsByVol(vol);
        
        // 3. Vérifier la disponibilité pour chaque ligne
        for (DemandeReservation ligne : lignesReservation) {
            Categorie categorie = ligne.getTarif().getCategorie();
            List<VolDetails> detailsForCategorie = volDetailsList.stream()
                .filter(vd -> vd.getTarif().getCategorie().getId() == categorie.getId())
                .collect(Collectors.toList());
            
            if (detailsForCategorie.isEmpty()) {
                System.out.println("Aucun détail trouvé pour la catégorie: " + categorie.getNom());
                return false;
            }
            
            int placesNeeded = ligne.getQuantite();
            int totalAvailable = detailsForCategorie.stream()
                .mapToInt(VolDetails::getNombreDisponible)
                .sum();
            
            if (totalAvailable < placesNeeded) {
                System.out.println("Pas assez de places disponibles pour la catégorie: " + categorie.getNom());
                return false;
            }
        }

        // 4. Préparer les données pour la mise à jour des places
        List<VolDetails> allDetailsToUpdate = new ArrayList<>();
        
        for (DemandeReservation ligne : lignesReservation) {
            TypeSiege typeSiege = ligne.getAvionDetailler().getTypeSiege();
            // Calcul des promotions et tarifs
            List<Promotion> promotions = promotionDAO.getPromotionsValidesParDate(vol, typeSiege, dateReservation);

            BigDecimal tarifBase = ligne.getTarif().getMontant();
            BigDecimal tarifFinal = tarifBase;
            Promotion promoAppliquee = null;

            for (Promotion promo : promotions) {
                if (promo.getQuantite() >= ligne.getQuantite()) {
                    tarifFinal = tarifBase.multiply(
                            BigDecimal.ONE.subtract(promo.getPourcentage().divide(BigDecimal.valueOf(100)))
                    );
                    promoAppliquee = promo;
                    break;
                }
            }

            BigDecimal montantTotal = tarifFinal.multiply(BigDecimal.valueOf(ligne.getQuantite()));

            ligne.setPassager(passager);
            ligne.setVol(vol);
            ligne.setDateDemande(LocalDateTime.now());
            ligne.setMontantTotal(montantTotal);
            ligne.setTarif(tarifDAO.getCurrentTarifByCategorie(ligne.getTarif().getCategorie()));

            // Insérer la réservation
            if (!insertReservation(ligne)) {
                System.out.println("Erreur lors de l'insertion");
                return false;
            }
            
            // Ajouter les détails à mettre à jour
            Categorie categorie = ligne.getTarif().getCategorie();
            List<VolDetails> detailsForCategorie = volDetailsList.stream()
                .filter(vd -> vd.getTarif().getCategorie().getId() == categorie.getId())
                .collect(Collectors.toList());
            
            allDetailsToUpdate.addAll(detailsForCategorie);
        }

        // 5. Mettre à jour les places pour toutes les réservations
        int totalPlaces = lignesReservation.stream()
            .mapToInt(DemandeReservation::getQuantite)
            .sum();
        
        VolDetailsDAO.decreaseNombrePlaces(vol, allDetailsToUpdate, totalPlaces);

        // 6. Mettre à jour les promotions
        for (DemandeReservation ligne : lignesReservation) {
              TypeSiege typeSiege = ligne.getAvionDetailler().getTypeSiege();
            List<Promotion> promotions = promotionDAO.getPromotionsValidesParDate(vol, typeSiege, dateReservation);
            Promotion promoAppliquee = null;

            for (Promotion promo : promotions) {
                if (promo.getQuantite() >= ligne.getQuantite()) {
                    promoAppliquee = promo;
                    break;
                }
            }

            if (promoAppliquee != null) {
                boolean ok = promotionDAO.decreaseAvailablePromotionSeats(promoAppliquee.getId(), ligne.getQuantite());
                if (!ok) {
                    System.out.println("Impossible de diminuer la quantité de la promotion pour " + ligne.getQuantite() + " places.");
                }
            }
        }

        return true;
    }

        // Méthode helper pour trouver les détails par catégorie
        private VolDetails trouverVolDetailsParCategorie(List<VolDetails> detailsList, Categorie categorie) {
            return detailsList.stream()
                .filter(vd -> vd.getTarif().getCategorie().getId() == categorie.getId())
                .findFirst()
                .orElse(null);
        }

   public static DemandeReservation getReservationById(int id) {
        String sql = "SELECT * FROM demande_reservation WHERE id = ?";
        try (Connection conn = Connexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    DemandeReservation demande = new DemandeReservation();
                    demande.setId(rs.getInt("id"));

                    Passager passager = Passager.getPassagerById(rs.getInt("id_passager"));
                    Vol vol = new VolDAO().getVolById(rs.getInt("id_vol"));
                    AvionDetailler avionDetailler = new AvionDetailsDAO().getById(rs.getInt("id_avion_siege"));
                    Tarif tarif = new TarifDAO().getTarifById(rs.getInt("id_tarif"));
                    ModePaiement modePaiement = ModePaiement.getById(rs.getInt("id_mode_paiement"));

                    demande.setPassager(passager);
                    demande.setVol(vol);
                    demande.setAvionDetailler(avionDetailler);
                    demande.setTarif(tarif);
                    demande.setQuantite(rs.getInt("quantite"));
                    demande.setModePaiement(modePaiement);
                    demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                    demande.setMontantTotal(rs.getBigDecimal("montant_total"));

                    return demande;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<DemandeReservation> getAllDemandeReservation() {
        List<DemandeReservation> demandes = new ArrayList<>();
        String sql = "SELECT * FROM demande_reservation ORDER BY date_demande DESC";

        try (Connection conn = Connexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                DemandeReservation demande = new DemandeReservation();
                demande.setId(rs.getInt("id"));

                Passager passager = Passager.getPassagerById(rs.getInt("id_passager"));
                Vol vol = new VolDAO().getVolById(rs.getInt("id_vol"));
                AvionDetailler avionDetailler = new AvionDetailsDAO().getById(rs.getInt("id_avion_siege"));
                Tarif tarif = new TarifDAO().getTarifById(rs.getInt("id_tarif"));
                ModePaiement modePaiement = ModePaiement.getById(rs.getInt("id_mode_paiement"));

                demande.setPassager(passager);
                demande.setVol(vol);
                demande.setAvionDetailler(avionDetailler);
                demande.setTarif(tarif);
                demande.setQuantite(rs.getInt("quantite"));
                demande.setModePaiement(modePaiement);
                demande.setDateDemande(rs.getTimestamp("date_demande").toLocalDateTime());
                demande.setMontantTotal(rs.getBigDecimal("montant_total"));

                demandes.add(demande);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return demandes;
    }

}
