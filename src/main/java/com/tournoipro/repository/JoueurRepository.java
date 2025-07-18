package com.tournoipro.repository;

import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {
    
    List<Joueur> findByEquipeId(Long equipeId);
    
    List<Joueur> findByType(TypeJoueur type);
    
    List<Joueur> findByNomContainingIgnoreCase(String nom);
    
    boolean existsByNomAndEquipeId(String nom, Long equipeId);
}

