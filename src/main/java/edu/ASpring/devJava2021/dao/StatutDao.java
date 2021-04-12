package edu.ASpring.devJava2021.dao;

import edu.ASpring.devJava2021.model.Statut;
import edu.ASpring.devJava2021.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutDao extends JpaRepository <Statut, Integer>{

}
