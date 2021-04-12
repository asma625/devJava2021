package edu.ASpring.devJava2021.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.ASpring.devJava2021.dao.CompetenceDao;
import edu.ASpring.devJava2021.model.Competence;
import edu.ASpring.devJava2021.model.Utilisateur;
import edu.ASpring.devJava2021.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CompetenceController {
    CompetenceDao competenceDao;
    //pour injecter toutes les interfaces de Repository
    @Autowired
    public CompetenceController(CompetenceDao competenceDao) {
        this.competenceDao = competenceDao;

    }


    @GetMapping("/competences")
    @JsonView({ CustomJsonView.VueCompetence.class})
    public List<Competence> getCompetences()
    {
       // List <Competence> listCompetence = competenceDao.findAll();


        return competenceDao.findAll();
    }


//AJOUTER OU METTRE à JOURS
    @PostMapping("/competence")
    public boolean  addCompetence(@RequestBody Competence competence){
          competenceDao.save(competence);
        return true;
    }
    @DeleteMapping("/competence/{id}")
    public boolean  deletCompetence(@PathVariable int id){
       competenceDao.deleteById(id);

        return true;
    }

    @GetMapping("/competence/{id}")
    @JsonView({ CustomJsonView.VueCompetence.class})
    public Competence  getCompetence(@PathVariable int id){

        return competenceDao.findById(id).orElse(null);
    }

}
