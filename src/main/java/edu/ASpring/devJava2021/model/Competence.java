package edu.ASpring.devJava2021.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.ASpring.devJava2021.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOgénéré les id
    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompetence.class})
    private int id;
    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompetence.class})
    private String nom;

    public List<Utilisateur> getListeUtilisateur() {
        return listeUtilisateur;
    }

    public void setListeUtilisateur(List<Utilisateur> listeUtilisateur) {
        this.listeUtilisateur = listeUtilisateur;
    }

    @ManyToMany(mappedBy = "listCompetence")
    @JsonView({ CustomJsonView.VueCompetence.class})
    private List<Utilisateur> listeUtilisateur;



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
