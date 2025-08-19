package controller;

import annotation.Url;
import connex.Connexion;
import annotation.Post;
import annotation.RequestParameter;
import annotation.*;
import tool.ModelAndView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.Vol;
import models.AvionDetailler;
import models.Categorie;
import models.VolDetails;
import models.TypeSiege;
import utilDAO.*;

@Controller
public class VolDetailsController {

    private VolDetailsDAO volDetailsDAO = new VolDetailsDAO();

    @Url(value = "/app/volDetails")
    @Get()
    public ModelAndView showFormulaire() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("/views/formulaireVolDetails");

        try {
            // Récupération des données pour le formulaire
            VolDAO volDAO = new VolDAO();
            CategorieDAO categorieDAO = new CategorieDAO();
            TypeSiegeDAO typeSiegeDAO = new TypeSiegeDAO();

            // Récupérer tous les vols, catégories et sièges
            List<Vol> vols = volDAO.getAllVols();
            List<TypeSiege> sieges = typeSiegeDAO.getAllSieges();
            List<Categorie> categories = categorieDAO.getAllCategories();

            // Ajouter ces données à l'objet ModelAndView
            modelAndView.add("vols", vols);
            modelAndView.add("sieges", sieges);
            modelAndView.add("categories", categories);

            System.out.println("Données récupérées avec succès.");
        } catch (Exception e) {
            modelAndView.add("error", "Erreur inattendue : " + e.getMessage());
        }

        return modelAndView;
    }
  
    @Url(value = "/app/insertVolDetails")
    @Post()
    public ModelAndView insertVolDetails(
        @RequestParameter("vol") int volId,
        @RequestParameter("siegeType") int typeSiegeId,
        @RequestParameter("categorie") int categorieId,
        @RequestParameter("nombreDisponible") int nombreDisponible) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("/views/confirmationVolDetails");
        try {
            // Récupérer le vol
            VolDAO volDAO = new VolDAO();
            Vol vol = volDAO.getVolById(volId);
                if (vol == null) {
                    throw new IllegalArgumentException("Vol non trouvé");
                }
    
    
            // Récupérer le TYPE de siège (Eco/Business...)
            TypeSiegeDAO typeSiegeDAO = new TypeSiegeDAO();
            TypeSiege typeSiege = typeSiegeDAO.getTypeSiegeById(typeSiegeId);
            if (typeSiege == null) throw new IllegalArgumentException("Type de siège invalide");

            // Récupérer les DÉTAILS de l'avion (nombre_siege pour ce type)
            AvionDetailsDAO avionDetailsDAO = new AvionDetailsDAO();
            AvionDetailler siege = avionDetailsDAO.getAvionDetaillerByAvionAndSiege(
                vol.getAvion().getId(), 
                typeSiege.getId() // ID du type de siège
            );
            if (siege == null) throw new IllegalArgumentException("Ce type de siège n'existe pas pour cet avion");

        
            // Récupérer la catégorie
            CategorieDAO categorieDAO = new CategorieDAO();
            Categorie categorie = categorieDAO.getCategorieById(categorieId);
            if (categorie == null) {
                throw new IllegalArgumentException("Catégorie non trouvée");
}
    
            // Insérer les détails du vol
            VolDetailsDAO volDetailsDAO = new VolDetailsDAO();
            VolDetails volDetails = volDetailsDAO.insererVolDetails(vol, nombreDisponible, siege, categorie);
            if (volDetails == null) {
                throw new SQLException("Échec de l'insertion des détails du vol");
            }
    
            modelAndView.add("success", "Détails du vol insérés avec succès");
        } catch (Exception e) {
            modelAndView.setUrl("/views/formulaireVolDetails");
            modelAndView.add("error", "Erreur lors de l'insertion : " + e.getMessage());
            // Récupérer à nouveau les données pour le formulaire
            VolDAO volDAO = new VolDAO();
            CategorieDAO categorieDAO = new CategorieDAO();
            TypeSiegeDAO typeSiegeDAO = new TypeSiegeDAO();
            modelAndView.add("vols", volDAO.getAllVols());
            modelAndView.add("sieges", typeSiegeDAO.getAllSieges());
            modelAndView.add("categories", categorieDAO.getAllCategories());
        }
        return modelAndView;
    }
}