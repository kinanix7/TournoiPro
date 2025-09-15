package com.tournoipro.repository;

import com.tournoipro.dto.EquipeWithPlayersDto;
import com.tournoipro.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    @Query("SELECT COUNT(e) FROM Equipe e")
    long countEquipes();

    List<Equipe> findByNom(String nom);

    List<Equipe> findByPouleId(Long pouleId);

    @Query("SELECT e FROM Equipe e WHERE e.poule IS NULL")
    List<Equipe> findEquipesSansPoule();

    /**
     * Find all teams with their basic information
     */
    @Query("SELECT e FROM Equipe e LEFT JOIN FETCH e.joueurs LEFT JOIN e.poule WHERE e.poule IS NOT NULL")
    List<Equipe> findAllEquipesWithPlayersAndPoule();
    
    /**
     * Find all teams with their basic information (including teams without poule)
     */
    @Query("SELECT e FROM Equipe e LEFT JOIN FETCH e.joueurs WHERE e.poule IS NULL")
    List<Equipe> findAllEquipesWithPlayersWithoutPoule();
}