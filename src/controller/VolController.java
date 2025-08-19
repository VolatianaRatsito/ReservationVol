package controller;

import annotation.*;
import connex.Connexion;
import jakarta.servlet.http.HttpServletRequest;
import tool.ModelAndView;
import utilDAO.AvionDAO;
import utilDAO.VilleDesservieDAO;
import utilDAO.VolDAO;
import utilDAO.VolDetailsDAO;
import models.Avion;
import models.VilleDesservie;
import models.Vol;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VolController {
  private final VolDAO volDAO;
    private final AvionDAO avionDAO;
    private final VilleDesservieDAO villeDesservieDAO;

    public VolController() {
        this.volDAO = new VolDAO();
        this.avionDAO = new AvionDAO();
        this.villeDesservieDAO = new VilleDesservieDAO();
    }

    @Url(value = "/app/vols")
    @Get()
    public ModelAndView showVols() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("/views/vols");
        try {
            List<Vol> vols = volDAO.getAllVols();
            modelAndView.add("vols", vols); // Vous passez la liste des vols ici
            System.out.println("Données récupérées avec succès.");
        } catch (Exception e) {
            modelAndView.add("error", "Erreur inattendue : " + e.getMessage());
        }
        return modelAndView;
    }

    @Url(value = "/app/update_redirect")
    @Get()
    public ModelAndView updateredirectVol(@RequestParameter("id") Integer idVol) {
        ModelAndView mav = new ModelAndView("/views/updateVol");
        try {
            Vol volToUpdate = volDAO.getVolById(idVol);
            List<Avion> avions = avionDAO.getAllAvions();
            List<VilleDesservie> villes = villeDesservieDAO.getAllVillesDesservies();
    
            mav.add("vol", volToUpdate);  // Ensure this line is present
            mav.add("avions", avions);
            mav.add("villes", villes);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    @Url(value = "/app/modifierVol")
    @Post()
    public ModelAndView modifierVol(@RequestParameter("id") int id,
                                     @RequestParameter("nom") String nom,
                                     @RequestParameter("idAvion") int idAvion,
                                     @RequestParameter("villeDepart") int villeDepartId,
                                     @RequestParameter("villeArriver") int villeArriverId,
                                     @RequestParameter("dateDepart") String dateDepartStr,
                                     @RequestParameter("dateArriver") String dateArriverStr) {
        ModelAndView modelAndView = new ModelAndView("/views/updateReussi");
        try {
            Avion avion = avionDAO.getAvionById(idAvion);
            VilleDesservie villeDepart = villeDesservieDAO.getVilleDesservieById(villeDepartId);
            VilleDesservie villeArriver = villeDesservieDAO.getVilleDesservieById(villeArriverId);

            LocalDateTime dateDepart = LocalDateTime.parse(dateDepartStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            LocalDateTime dateArriver = LocalDateTime.parse(dateArriverStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            Vol vol = new Vol(id, nom, avion, villeDepart, villeArriver, dateDepart, dateArriver);
            volDAO.updateVol(vol);
            System.out.println("Flight updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @Url(value = "/app/vols/supprimer/{id}")
    @Post()
    public ModelAndView supprimer(@RequestParameter("id") int id) {
        try {
            volDAO.deleteVol(id);
            System.out.println("Flight deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting flight: " + e.getMessage());
        }
        return new ModelAndView("/views/updateReussi");
    }

    @Url(value = "/app/insert_redirect")
    @Get()
    public ModelAndView insertredirectVol(@RequestParameter("id") Integer idVol) {
        ModelAndView mav = new ModelAndView("/views/insertVol");
        try {
            List<Avion> avions = avionDAO.getAllAvions();
            List<VilleDesservie> villes = villeDesservieDAO.getAllVillesDesservies();

            mav.add("avions", avions);
            mav.add("villes", villes);

        } catch (Exception e) {
            e.printStackTrace();
            mav.add("error", "Erreur lors de la récupération des données : " + e.getMessage());
        }
        
        return mav;
    }
   
    private boolean verifierDonneesVol(Vol vol) {
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

    @Url(value = "/app/insertVol")
    @Post()
    public ModelAndView insertVol(@RequestParameter("nom") String nom,
                                @RequestParameter("idAvion") int idAvion,
                                @RequestParameter("villeDepart") int villeDepartId,
                                @RequestParameter("villeArriver") int villeArriverId,
                                @RequestParameter("dateDepart") String dateDepartStr,
                                @RequestParameter("dateArriver") String dateArriverStr) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/vols");  // Redirection explicite
        Vol vol = new Vol();

        try (Connection conn = Connexion.getConnection()) {
            conn.setAutoCommit(false);

            // Récupérer l'avion
            Avion avion = avionDAO.getAvionById(idAvion);
            if (avion == null) {
                throw new Exception("Avion non trouvé avec l'ID : " + idAvion);
            }

            // Assigner les valeurs à l'objet Vol
            vol.setNom(nom);
            vol.setAvion(avion);
            vol.setVilleDepart(villeDesservieDAO.getVilleDesservieById(villeDepartId));
            vol.setVilleArriver(villeDesservieDAO.getVilleDesservieById(villeArriverId));
            vol.setDateDepart(LocalDateTime.parse(dateDepartStr, DateTimeFormatter.ofPattern("yyyy-MM-dd':'HH:mm")));
            vol.setDateArriver(LocalDateTime.parse(dateArriverStr, DateTimeFormatter.ofPattern("yyyy-MM-dd':'HH:mm")));

            // Valider les données du vol
            if (!verifierDonneesVol(vol)) {
                throw new Exception("Validation des données du vol échouée.");
            }

            // Insérer le vol dans la table `vol`
            boolean success = volDAO.insertVol(conn, vol);
            if (!success) {
                throw new Exception("Erreur lors de l'insertion du vol.");
            }

            conn.commit();
            System.out.println("Vol inséré avec succès !");

            // Ajouter un message de succès
            modelAndView.add("success", "Le vol a été inséré avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());

            // En cas d'erreur, rediriger vers la page d'insertion avec un message d'erreur
            modelAndView = new ModelAndView("/views/insertVol");
            modelAndView.add("error", "Erreur lors de l'insertion du vol : " + e.getMessage());

            // Récupérer les avions et les villes pour réafficher le formulaire
            List<Avion> avions = avionDAO.getAllAvions();
            List<VilleDesservie> villes = villeDesservieDAO.getAllVillesDesservies();
            modelAndView.add("avions", avions);
            modelAndView.add("villes", villes);
        }
        return modelAndView;
    }
    
    @Url(value = "/app/searchVols_redirect")
    @Get()
    public ModelAndView searchVolsredirectVol() {
        ModelAndView mav = new ModelAndView("/views/search");
        try {
            List<Avion> avions = avionDAO.getAllAvions();
            List<VilleDesservie> villes = villeDesservieDAO.getAllVillesDesservies();

            mav.add("avions", avions);
            mav.add("villes", villes);
        } catch (Exception e) {
            e.printStackTrace();
            mav.add("error", "Erreur lors de la récupération des données : " + e.getMessage());
        }
        return mav;
    }

    @Url(value = "/app/searchVols")
    @Post()
    public ModelAndView rechercherVols(@RequestParameter("ville") String villeNom,
                                        @RequestParameter("dateDepart") String dateDepartStr,
                                        @RequestParameter("dateArriver") String dateArriveeStr,
                                        @RequestParameter("idAvion") int idAvion) {
        ModelAndView modelAndView = new ModelAndView("/views/searchResult");
        try {
            // Traitement des paramètres
            VilleDesservie villeRecherche = new VilleDesservie(0, villeNom);
            LocalDateTime dateDepart = LocalDateTime.parse(dateDepartStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            LocalDateTime dateArriver = LocalDateTime.parse(dateArriveeStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            Avion avion = (idAvion > 0) ? avionDAO.getAvionById(idAvion) : null;
            if (idAvion > 0 && avion == null) {
                System.out.println("Error: Aucun avion trouvé avec cet ID.");
            }

            List<Vol> vols = volDAO.searchVolsByCriteria(villeRecherche, dateDepart.toLocalDate(), dateArriver.toLocalDate(), avion);
            modelAndView.add("vols", vols);
            modelAndView.add("villes", villeDesservieDAO.getAllVillesDesservies());
            modelAndView.add("avions", avionDAO.getAllAvions());
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
        }
        return modelAndView;
    }
}