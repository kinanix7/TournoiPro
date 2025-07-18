package com.tournoipro.repository;

import com.tournoipro.model.Classement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassementRepository extends JpaRepository<Classement, Long> {
    
    Optional<Classement> findByEquipeId(Long equipeId);
    
    @Query("SELECT c FROM Classement c ORDER BY c.points DESC, c.victoire DESC, c.defaite ASC")
    List<Classement> findAllOrderByPointsDesc();
    
    @Query("SELECT c FROM Classement c WHERE c.equipe.poule.id = :pouleId ORDER BY c.points DESC, c.victoire DESC, c.defaite ASC")
    List<Classement> findByPouleIdOrderByPointsDesc(Long pouleId);
}

