// package com.itu.controller;

// import view.*;
// import file.MyFile;
// import auth.Auth;
// import auth.Auth_classe;
// import com.itu.connection.*;
// import com.itu.user.*;
// import utils.MySession;
// import user.UserInterface;

// import java.sql.Connection;
// // import java.util.*;
// import java.sql.Date;
// import java.sql.SQLException;

// import annotation.Controller;
// import annotation.Get;
// import annotation.Post;
// import annotation.Url;
// import annotation.ParameterAnnotation;
// import annotation.Restapi; 
// import annotation.ErrorPage;
// import java.io.File; // Pour manipuler les fichiers
// import java.io.FileOutputStream; // Pour écrire des données dans un fichier
// import java.io.IOException; // Pour gérer les exceptions d'E/S
// import java.lang.reflect.Parameter;

// @Auth_classe(profil = {"ADMIN", "Passager"})
// @Controller(name = "/api/login")
// public class LoginController implements UserInterface {
    
//     @Url(name= "/user/login")
//     @ErrorPage("/web/login.jsp")
//     @Auth(profil = {"INFORMATIQUE", "Passager", "ADMIN"})
//     @Post 
//     public ModelView check_log(@ParameterAnnotation(name= "email") String email, @ParameterAnnotation(name = "password") String password){
//         Users user = new Users();
//         user.setEmail(email); 
//         System.out.println("email : " + email);
//         System.out.println("password : " + password);
//         user.setPassword(password);
//         ModelView view = new ModelView();
//         try(Connection connection = Connexion.connect()){
//             Users val = user.Login(connection);
//             System.out.println("user_valiny///////////////////////////////// " + val.getEmail());
//             System.out.println("user_valiny///////////////////////////////// " + val.getPassword());
//             System.out.println("user_valiny///////////////////////////////// " + val.getId());
//             if(val.getEmail() != null){
//                 view.add("log", val); 
//                 view.setUrl("/user/vol");
//             } else {
//                 view.add("erreur", "Mot de passe ou adresse electronique erroné");
//                 view.setUrl("/web/login.jsp");
//             }    
//         } catch(SQLException e){
//             e.getMessage();
//         }  
//         return view;
//     }        

//     @Url(name= "role")
//     @Override
//     public String[] getRoles() {
//         String[] users = {"ADMIN", "USER", "GUEST"};
//         return users;
//     }      
// }
