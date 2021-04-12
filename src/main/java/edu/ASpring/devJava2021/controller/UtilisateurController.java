package edu.ASpring.devJava2021.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.ASpring.devJava2021.dao.UtilisateurDao;
import edu.ASpring.devJava2021.model.Competence;
import edu.ASpring.devJava2021.model.Utilisateur;
import edu.ASpring.devJava2021.security.JwtUtil;
import edu.ASpring.devJava2021.security.UserDetailsServiceCustom;
import edu.ASpring.devJava2021.view.CustomJsonView;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class UtilisateurController {
    UtilisateurDao utilisateurDao;
    JwtUtil jwtUtil;
    AuthenticationManager authenticationManager;
    UserDetailsServiceCustom userDetailsServiceCustom;
    //pour injecter toutes les interfaces de Repository
    @Autowired
    public UtilisateurController(UtilisateurDao utilisateurDao, JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                                 UserDetailsServiceCustom userDetailsServiceCustom)  {
        this.utilisateurDao = utilisateurDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
    }


     @PostMapping("/authentification")
     public  String authentification(@RequestBody Utilisateur utilisateur){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        utilisateur.getLogin() , utilisateur.getPassword()
                )
        );
         UserDetails userDetails = this.userDetailsServiceCustom.loadUserByUsername(utilisateur.getLogin());
         return jwtUtil.generateToken(userDetails);
     }



    @JsonView(CustomJsonView.VueUtilisateur.class)
    @GetMapping("/user/utilisateurs")
    public List<Utilisateur> getUtlisateurs()
    {
       /* List<Utilisateur> listUtilisateur =  utilisateurDao.findAll();
        for (Utilisateur utilisateur:listUtilisateur) {
            for (Competence competence :utilisateur.getListCompetence()) {
                competence.setListeUtilisateur(null);
            }
            utilisateur.getStatut().setListeUtilisateur(null);
        }*/
        return utilisateurDao.findAll();
    }

    @PostMapping("/admin/utilisateur")
    public boolean  addUtilisateur(@RequestBody Utilisateur utilisateur){
          utilisateurDao.save(utilisateur);
        return true;
    }
    @DeleteMapping("/admin/utilisateur/{id}")
    public boolean  deletUtilisateur(@PathVariable int id){
       utilisateurDao.deleteById(id);

        return true;
    }
    @JsonView(CustomJsonView.VueUtilisateur.class)
    @GetMapping("/user/utilisateur/{id}")
    public Utilisateur  getUtilisateur(@PathVariable int id){
         //Utilisateur utilisateur  = utilisateurDao.findById(id).orElse(null);
        return utilisateurDao.findById(id).orElse(null);
    }

}
