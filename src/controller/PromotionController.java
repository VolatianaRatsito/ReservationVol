package controller;

import annotation.*;
import models.AvionDetailler;
import models.Promotion;
import models.Vol;
import models.TypeSiege;
import tool.ModelAndView;
import utilDAO.AvionDetailsDAO;
import utilDAO.PromotionDAO;
import utilDAO.VolDAO;
import utilDAO.TypeSiegeDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date; // Importer java.sql.Date
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class PromotionController {
    private PromotionDAO promotionDAO;
    private VolDAO volDAO;
    private AvionDetailsDAO avionDetailsDAO; // Renommé pour plus de clarté

    public PromotionController() {
        this.promotionDAO = new PromotionDAO();
        this.volDAO = new VolDAO();
        this.avionDetailsDAO = new AvionDetailsDAO(); // Initialisation corrigée
    }

    @Url(value = "/app/list")
    @Get()
    public ModelAndView showPromotion() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("/views/promotion-result");
        try {
            List<Promotion> promotions = promotionDAO.getAllPromotions();
            modelAndView.add("promotions", promotions);
            System.out.println("Données récupérées avec succès.");
        } catch (Exception e) {
            modelAndView.add("error", "Erreur inattendue : " + e.getMessage());
        }
        return modelAndView;
    }
 
    @Url(value = "/app/add-form")
    @Get()
    public ModelAndView showAddForm() {
        ModelAndView mv = new ModelAndView("/views/promotion-form");
    
        try {
            List<Vol> vols = volDAO.getAllVols();
            mv.add("vols", vols);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des vols : " + e.getMessage());
            mv.add("error", "Erreur lors de la récupération des vols.");
        }
    
        try {
            List<AvionDetailler> avionDetaillers = avionDetailsDAO.getAllAvionDetailler();
            mv.add("avionDetaillers", avionDetaillers);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des configurations sièges : " + e.getMessage());
            mv.add("error", "Erreur lors de la récupération des configurations sièges.");
        }
    
        return mv;
    }

    @Url(value = "/app/add")
    @Post()
    public ModelAndView insertPromotion(
            @RequestParameter("datePromotion") String datePromotion,
            @RequestParameter("dateExpiration") String dateExpiration,
            @RequestParameter("volId") int volId,
            @RequestParameter("avionDetaillerId") int avionDetaillerId,
            @RequestParameter("quantite") int quantite,
            @RequestParameter("pourcentage") int pourcentageValue) {

        ModelAndView modelAndView = new ModelAndView("/views/confirmationPromotion");

        try {
            // Récupérer le vol
            Vol vol = volDAO.getVolById(volId);
            if (vol == null) {
                throw new Exception("Vol non trouvé avec l'ID : " + volId);
            }

            // Récupérer AvionDetailler par ID
            AvionDetailler avionSiege = avionDetailsDAO.getById(avionDetaillerId);
            if (avionSiege == null) {
                throw new Exception("Configuration siège non trouvée avec l'ID : " + avionDetaillerId);
            }

            // Convertir les dates
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate promoDate = LocalDate.parse(datePromotion, formatter);
            LocalDate expDate = LocalDate.parse(dateExpiration, formatter);

            // Validation du pourcentage
            BigDecimal pourcentage = new BigDecimal(pourcentageValue);
            if (pourcentage.compareTo(BigDecimal.ZERO) < 0 
                || pourcentage.compareTo(new BigDecimal(100)) > 0) {
                throw new IllegalArgumentException("Le pourcentage doit être entre 0 et 100");
            }

            // Créer et insérer la promotion
            Promotion promotionObj = new Promotion();
            promotionObj.setVol(vol);
            promotionObj.setAvionSiege(avionSiege);
            promotionObj.setDatePromotion(promoDate);
            promotionObj.setDateExpiration(expDate);
            promotionObj.setQuantite(quantite);
            promotionObj.setPourcentage(pourcentage);

            Promotion addedPromotion = promotionDAO.addPromotion(
                promoDate,
                expDate,
                vol,
                avionSiege,
                quantite,
                pourcentage
            );

            if (addedPromotion == null) {
                throw new Exception("Erreur lors de l'insertion de la promotion.");
            }

            modelAndView.add("success", "Promotion insérée avec succès !");
            
        } catch (Exception e) {
            modelAndView = new ModelAndView("/views/promotion-form");
            modelAndView.add("error", "Erreur : " + e.getMessage());

            // Réinitialiser les données du formulaire
            List<Vol> vols = volDAO.getAllVols();
            List<AvionDetailler> avionDetaillers = avionDetailsDAO.getAllAvionDetailler();
            modelAndView.add("vols", vols);
            modelAndView.add("avionDetaillers", avionDetaillers); 
        }
        return modelAndView;
    }

    // Supprimer une promotion
    @Url(value = "/app/delete")
    @Post()
    public ModelAndView deletePromotion(@RequestParameter("promotionId") int promotionId,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/views/promotion-result");
        boolean success = promotionDAO.deletePromotion(promotionId);

        mv.add("operation", "suppression");
        mv.add("success", success);
        mv.add("message", success ? "Promotion supprimée avec succès" : "Échec de la suppression");
        return mv;
    }

    // Vérifier la disponibilité d'une promotion (API)
    @Url(value = "/app/check-availability")
    @Get()
    public String checkPromotionAvailability(@RequestParameter("volId") int volId,
                                             @RequestParameter("typeSiegeId") int typeSiegeId,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        boolean isAvailable = promotionDAO.isPromotionAvailable(volId, typeSiegeId);
        return isAvailable ? "disponible" : "indisponible";
    }

    // Appliquer une promotion (API)
    @Url(value = "/app/apply")
    @Post()
    public String applyPromotion(@RequestParameter("promotionId") int promotionId,
                                @RequestParameter("quantite") int quantite, // Ajouter ce paramètre
                                HttpServletRequest request,
                                HttpServletResponse response) {
        boolean success = promotionDAO.decreaseAvailablePromotionSeats(promotionId, quantite);
        return success ? "appliquee" : "echec";
    }
}