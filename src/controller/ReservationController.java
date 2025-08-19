package controller;

import annotation.*;
import tool.ModelAndView;
import models.*;
import utilDAO.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Controller
public class ReservationController {

    private DemandeReservationDAO demandeReservationDAO = new DemandeReservationDAO();
    private VolDAO volDAO = new VolDAO();
    private CategorieDAO categorieDAO = new CategorieDAO();
    private TypeSiegeDAO typeSiegeDAO = new TypeSiegeDAO();
    private AvionDetailsDAO avionDetaillerDAO = new AvionDetailsDAO();

    @Url(value = "/app/reservationList")
    @Get()
    public ModelAndView showDemandeReservation() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("/views/reservations");
        try {
            List<DemandeReservation> reservations = demandeReservationDAO.getAllDemandeReservation();
            modelAndView.add("reservations", reservations);
            System.out.println("Données récupérées avec succès.");
        } catch (Exception e) {
            modelAndView.add("error", "Erreur inattendue : " + e.getMessage());
        }
        return modelAndView;
    }
    
    @Url(value = "/app/annulerReservation")
    @Post()
    public ModelAndView annulerReservation(
            @RequestParameter("demandeId") int demandeId,
            @RequestParameter("motif") String motif) {

        ModelAndView model = new ModelAndView("/views/reservations");

        try {
            DemandeReservationDAO demandeReservationDAO = new DemandeReservationDAO();
            AnnulationReservationDAO annulationDAO = new AnnulationReservationDAO();

            DemandeReservation demande = demandeReservationDAO.getReservationById(demandeId);
            if (demande == null) {
                model.add("error", "Réservation introuvable.");
                try {
                    return showDemandeReservation();
                } catch (SQLException e) {
                    e.printStackTrace();
                    model.add("error", "Erreur technique lors de la récupération des réservations : " + e.getMessage());
                    return model;
                }
            }

            boolean ok = annulationDAO.annulerReservation(demande, motif);
            if (ok) {
                model.add("success", "Réservation annulée avec succès !");
            } else {
                model.add("error", "Échec de l'annulation. Vérifiez les règles d'annulation.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.add("error", "Erreur technique : " + e.getMessage());
        }

        try {
            return showDemandeReservation();
        } catch (SQLException e) {
            e.printStackTrace();
            model.add("error", "Erreur technique lors de la récupération des réservations : " + e.getMessage());
            return model;
        }
    }


    @Url(value = "/app/demande")
    @Get()
    public ModelAndView showFormulaire() {
        ModelAndView modelAndView = new ModelAndView("/views/addReservation");

        try {
            // Récupération des données pour le formulaire
            List<Passager> passagers = Passager.getAllPassagers();
            List<Vol> vols = volDAO.getAllVols();
            List<TypeSiege> sieges = typeSiegeDAO.getAllSieges();
            List<Categorie> categories = categorieDAO.getAllCategories();
            List<ModePaiement> modesPaiement = ModePaiement.getAllModePaiement();

            // Récupérer la liste AvionDetailler
            List<AvionDetailler> avionDetaillers = avionDetaillerDAO.getAllAvionDetailler(); // méthode à créer si besoin

            Map<Integer, BigDecimal> tarifsParCategorie = new HashMap<>();
            TarifDAO tarifDAO = new TarifDAO();

            for (Categorie categorie : categories) {
                Tarif tarif = tarifDAO.getCurrentTarifByCategorie(categorie);
                BigDecimal montant = (tarif != null) ? tarif.getMontant() : BigDecimal.ZERO;
                tarifsParCategorie.put(categorie.getId(), montant);
            }

            modelAndView.add("passagers", passagers);
            modelAndView.add("vols", vols);
            modelAndView.add("sieges", sieges);
            modelAndView.add("categories", categories);
            modelAndView.add("modesPaiement", modesPaiement);
            modelAndView.add("tarifs", tarifsParCategorie);
            modelAndView.add("avionDetaillers", avionDetaillers);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.add("error", "Erreur inattendue : " + e.getMessage());
        }

        return modelAndView;
    }


    @Url(value = "/app/reserver")
    @Post()
    public ModelAndView createReservation(
            @RequestParameter("passagerId") int passagerId,
            @RequestParameter("volId") int volId,
            @RequestParameter("avionDetaillerId") int avionDetaillerId,
            @RequestParameter("categorieId") int categorieId,
            @RequestParameter("quantite") int quantite,
            @RequestParameter("modePaiementId") int modePaiementId) {
        ModelAndView model = new ModelAndView("/views/addReservation");

        try {
            Passager passager = Passager.getPassagerById(passagerId);
            Vol vol = volDAO.getVolById(volId);
            ModePaiement modePaiement = ModePaiement.getById(modePaiementId);
            Categorie categorie = categorieDAO.getCategorieById(categorieId);
            AvionDetailler avionDetailler = avionDetaillerDAO.getById(avionDetaillerId);

            if (passager == null || vol == null || modePaiement == null || categorie == null || avionDetailler == null) {
                throw new Exception("Données invalides. Veuillez vérifier les informations fournies.");
            }

            TarifDAO tarifDAO = new TarifDAO();
            Tarif tarif = tarifDAO.getCurrentTarifByCategorie(categorie);
            if (tarif == null) {
                throw new Exception("Aucun tarif trouvé pour cette catégorie");
            }

            DemandeReservation ligne = new DemandeReservation();
            ligne.setPassager(passager);
            ligne.setVol(vol);
            ligne.setAvionDetailler(avionDetailler);
            ligne.setTarif(tarif);
            ligne.setQuantite(quantite);
            ligne.setModePaiement(modePaiement);
            ligne.setDateDemande(java.time.LocalDateTime.now());
            ligne.setMontantTotal(tarif.getMontant().multiply(BigDecimal.valueOf(quantite)));

            List<DemandeReservation> lignes = new ArrayList<>();
            lignes.add(ligne);

            boolean success = demandeReservationDAO.faireReservationMultiple(passager, vol, lignes, java.time.LocalDate.now());

            if (success) {
                model.add("success", "Réservation créée avec succès !");
            } else {
                model.add("error", "Échec de la création de la réservation.");
            }

            // Recharge des données formulaire comme dans showFormulaire()
            return showFormulaire();

        } catch (Exception e) {
            e.printStackTrace();
            model.add("error", "Erreur technique: " + e.getMessage());
        }

        return model;
    }


}