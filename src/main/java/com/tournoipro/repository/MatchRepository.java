package com.tournoipro.repository;

import com.tournoipro.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    
    List<Match> findByPouleId(Long pouleId);
    
    List<Match> findByEquipe1IdOrEquipe2Id(Long equipe1Id, Long equipe2Id);
    
    List<Match> findByDate(LocalDate date);
    
    List<Match> findByTermine(Boolean termine);
    
    List<Match> findByTerrainId(Long terrainId);
    
    List<Match> findByArbitreId(Long arbitreId);
    
    @Query("SELECT m FROM Match m WHERE (m.equipe1.id = :equipeId OR m.equipe2.id = :equipeId) AND m.termine = true")
    List<Match> findMatchsTerminesParEquipe(@Param("equipeId") Long equipeId);
    
    @Query("SELECT m FROM Match m WHERE m.date >= :dateDebut AND m.date <= :dateFin ORDER BY m.date, m.heure")
    List<Match> findMatchsBetweenDates(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
}

