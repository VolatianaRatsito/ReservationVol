package controller;

import annotation.*;
import tool.ModelAndView;
import utilDAO.NationaliteDAO;
import utilDAO.PassportDAO;

import models.Passager;
import models.Passport;
import models.FileUpload;
import models.Nationalite;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PassportController {

    private final PassportDAO passportDAO = new PassportDAO();
    private final NationaliteDAO nationaliteDAO = new NationaliteDAO();

    @Url(value = "/app/listPassport")
    @Get()
    public ModelAndView listPassports() {
        try {
            List<Passport> passports = passportDAO.getAllPassports();
            ModelAndView mv = new ModelAndView("/views/list");
            mv.add("passports", passports);
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            ModelAndView error = new ModelAndView("/views/erreur");
            error.add("message", "Erreur lors de la récupération des passeports.");
            return error;
        }
    }

    @Url(value = "/app/addPassport")
    @Get()
    public ModelAndView showAddForm() {
        ModelAndView mav = new ModelAndView("/views/add");
        try {
            List<Nationalite> nationalites = nationaliteDAO.getAllNationalites();
            List<Passager> passagers = Passager.getAllPassagers();

            mav.add("nationalites", nationalites);
            mav.add("passagers", passagers);
        } catch (Exception e) {
            e.printStackTrace();
            mav.add("error", "Erreur lors de la récupération des données : " + e.getMessage());
        }
        return mav;
    }

    @Post()
    @Url(value = "/app/save")
    public ModelAndView savePassport(
        @RequestParameter("idPassager") int idPassager,
        @RequestParameter("idNationalite") int idNationalite,
        @RequestParameter("date_emission") String dateEmission,
        @RequestParameter("date_expiration") String dateExpiration,
        @RequestParameter("photo") FileUpload fileUpload) {

        ModelAndView mav = new ModelAndView("/views/add");

        try {
            Passport passport = new Passport();
            passport.setPassager(new Passager(idPassager));
            passport.setNationalite(new Nationalite(idNationalite, null));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            passport.setDateEmission(Date.valueOf(LocalDate.parse(dateEmission, formatter)));
            passport.setDateExpiration(Date.valueOf(LocalDate.parse(dateExpiration, formatter)));

            if (fileUpload != null && fileUpload.getSize() > 0) {
                String uploadDirectory = "C:\\apache-tomcat-10.1.7\\webapps\\ReservationVol\\uploads\\";

                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    boolean created = uploadDir.mkdirs();
                    if (!created) {
                        mav.add("errorMessage", "Impossible de créer le dossier pour stocker les photos.");
                        return mav;
                    }
                }

                // Nettoyer le nom du fichier pour éviter problèmes
                String originalFileName = fileUpload.getOriginalFileName();
                String safeFileName = System.currentTimeMillis() + "_" + originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

                File destination = new File(uploadDir, safeFileName);

                // Copie du fichier uploadé dans le dossier
                try (InputStream input = fileUpload.getInputStream()) {
                    java.nio.file.Files.copy(input, destination.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    mav.add("errorMessage", "Erreur lors de la copie de l'image : " + ioe.getMessage());
                    return mav;
                }

                passport.setPhoto(safeFileName);
            } else {
                passport.setPhoto(null);
            }

            passportDAO.insertPassport(passport);

            mav.add("successMessage", "Passeport enregistré avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            mav.add("errorMessage", "Erreur lors de l'enregistrement : " + e.getMessage());
        }

        try {
            mav.add("nationalites", nationaliteDAO.getAllNationalites());
            mav.add("passagers", Passager.getAllPassagers());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mav;
    }

}