package edu.ASpring.devJava2021.controller;


import edu.ASpring.devJava2021.dao.StatutDao;
import edu.ASpring.devJava2021.dao.StatutDao;
import edu.ASpring.devJava2021.model.Statut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class StatutController {
    StatutDao statutDao;
    //pour injecter toutes les interfaces de Repository
    @Autowired
    public StatutController(StatutDao statutDao) {
        this.statutDao = statutDao;

    }


    @GetMapping("/user/statuts")
    public List<Statut> getStatuts()
    {
        List<Statut> listStatut = statutDao.findAll();
        return listStatut;
    }


//AJOUTER OU METTRE à JOURS
    @PostMapping("/admin/statut")
    public boolean  addStatut(@RequestBody Statut statut){
          statutDao.save(statut);
        return true;
    }
    @DeleteMapping("/admin/statut/{id}")
    public boolean  deletStatut(@PathVariable int id){
       statutDao.deleteById(id);

        return true;
    }

    @GetMapping("/user/statut/{id}")
    public Statut  getStatut(@PathVariable int id){

        return statutDao.findById(id).orElse(null);
    }

}
