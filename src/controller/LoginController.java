package controller;

import controller.principal.*; 
import annotation.*;
import tool.*;

import java.sql.Connection;
import java.sql.SQLException;
import models.*;
import connex.*;

@Controller
public class LoginController {

    @Url(value="/app/login")
    @Get()
    public ModelAndView showFormulaire() {
        ModelAndView mav = new ModelAndView("/views/login");
        return mav;
    }

    @Url(value="/app/SubmitForm")
    @Post()
    public ModelAndView validateForm(@RequestParameter("email") String mailString,@RequestParameter("password") String mdpString,@ModelAttribute MySession mysession) throws ClassNotFoundException, SQLException {

       System.out.println("je suis dans validateform");
       ModelAndView reussi = new ModelAndView("/views/accueil");
       ModelAndView echec = new ModelAndView("/views/login");

       Connection c = Connexion.getConnection();
       
       Passager admin = new Passager(mailString, mdpString);
       boolean isLogged = admin.login(c);

       if (isLogged) {
            mysession.add("userSessionKey",true);
            System.out.println("Session 1 cree");
            mysession.add("userRole","admin");
            System.out.println("Session 2 cree");
            
       }
       else{
           System.out.println("Login ou mot de passe incorrect");
           return echec;
       }
       return reussi;
    }    

    @Url(value="/app/SubmitForm")
    @Post()
    @RestApi()
    public ModelAndView loginRest(@RequestParameter("email") String mailString,@RequestParameter("password") String mdpString,@ModelAttribute MySession mysession) throws ClassNotFoundException, SQLException {

       System.out.println("je suis dans validateform");
       ModelAndView reussi = new ModelAndView("/views/accueil");
       ModelAndView echec = new ModelAndView("/views/login");

       Connection c = Connexion.getConnection();
       
       Passager admin = new Passager(mailString, mdpString);
       boolean isLogged = admin.login(c);

       if (isLogged) {
            mysession.add("userSessionKey",true);
            System.out.println("Session 1 cree");
            mysession.add("userRole","admin");
            System.out.println("Session 2 cree");
            
       }
       else{
           System.out.println("Login ou mot de passe incorrect");
           return echec;
       }
       return reussi;
    }    
}
