package edu.ASpring.devJava2021.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.ASpring.devJava2021.view.CustomJsonView;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOgénéré les id
    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompetence.class})
    private int id;


    @Column(nullable = false ,length =50)
    @JsonView({CustomJsonView.VueUtilisateur.class, CustomJsonView.VueCompetence.class})
    private String login;
    private String  password;

    /*@ManyToOne
    @JsonView({CustomJsonView.VueUtilisateur.class})
    private Role role;*/

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonView({CustomJsonView.VueUtilisateur.class})
    @JoinTable(
            name = "utilisateur_role",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))


    private  List<Role> listRole;

    @ManyToOne
    @JsonView({CustomJsonView.VueUtilisateur.class})
    private Statut statut;

    @ManyToMany
    @JsonView({CustomJsonView.VueUtilisateur.class})
    @JoinTable(
            name = "Utlisateur_competence",
            joinColumns = @JoinColumn (name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> listCompetence;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public List<Competence> getListCompetence() {
        return listCompetence;
    }

    public void setListCompetence(List<Competence> listCompetence) {
        this.listCompetence = listCompetence;
    }

    /*public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }*/

    public List<Role> getListRole() {
        return listRole;
    }

    public void setListRole(List<Role> listRole) {
        this.listRole = listRole;
    }
}
