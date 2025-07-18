package com.tournoipro.repository;

import com.tournoipro.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    
    Optional<Equipe> findByNom(String nom);
    
    List<Equipe> findByPouleId(Long pouleId);
    
    @Query("SELECT e FROM Equipe e WHERE e.poule IS NULL")
    List<Equipe> findEquipesSansPoule();
    
    boolean existsByNom(String nom);
}

