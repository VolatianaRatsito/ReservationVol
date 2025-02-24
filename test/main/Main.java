package main;

import utilDAO.VolDAO;
import models.Avion;
import models.VilleDesservie;
import models.Vol;
import models.AvionDetailler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Instanciation du DAO
        VolDAO volDAO = new VolDAO();

        // ------------------------------
        // Test de la méthode getAllVols()
        // ------------------------------
        testGetAllVols(volDAO);

        // ----------------------------------------------
        // Test de la méthode searchVolsByCriteria()
        // ----------------------------------------------
        testSearchVolsByCriteria(volDAO);

        // ------------------------------------------------------
        // Test de la méthode insertVol() avec un vol fictif
        // ------------------------------------------------------
        // testInsertVol(volDAO);
    }

    private static void testGetAllVols(VolDAO volDAO) {
        try {
            System.out.println("Liste de tous les vols :");
            List<Vol> tousLesVols = volDAO.getAllVols();
            if (tousLesVols.isEmpty()) {
                System.out.println("Aucun vol trouvé.");
            } else {
                tousLesVols.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la récupération des vols : " + e.getMessage());
        }
    }

    private static void testSearchVolsByCriteria(VolDAO volDAO) {
        try {
            VilleDesservie villeRecherche = new VilleDesservie(0, "Abuja");
            LocalDate dateDebut = LocalDate.parse("2025-03-12");
            LocalDate dateFin = LocalDate.parse("2025-03-12");
            Avion avionRecherche = new Avion(0, "Airbus A320-200", null, null);
    
            System.out.println("\nRecherche de vols par critères :");
            List<Vol> volsRecherche = volDAO.searchVolsByCriteria(villeRecherche, dateDebut, dateFin, avionRecherche);
            if (volsRecherche.isEmpty()) {
                System.out.println("Aucun vol trouvé pour les critères donnés.");
            } else {
                volsRecherche.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Une erreur est survenue lors de la recherche de vols : " + e.getMessage());
        }
    }

    // private static void testInsertVol(VolDAO volDAO) {
    //     try {
    //         Avion avionTest = new Avion(1, "Airbus A320", null, LocalDateTime.now().minusYears(10));
    //         VilleDesservie villeDepart = new VilleDesservie(1, "Lyon");
    //         VilleDesservie villeArriver = new VilleDesservie(2, "Marseille");
    //         LocalDateTime depart = LocalDateTime.now().plusDays(2);
    //         LocalDateTime arriver = depart.plusHours(1);
    //         Vol nouveauVol = new Vol(0, "Vol Test", avionTest, villeDepart, villeArriver, depart, arriver);

    //         System.out.println("\nInsertion d'un nouveau vol...");
    //         boolean insertionResult = volDAO.insertVol(nouveauVol);
    //         if (insertionResult) {
    //             System.out.println("Le vol a été inséré avec succès.");
    //         } else {
    //             System.out.println("L'insertion du vol a échoué.");
    //         }
    //     } catch (Exception e) {
    //         System.err.println("Une erreur est survenue lors de l'insertion du vol : " + e.getMessage());
    //     }
    // }
}