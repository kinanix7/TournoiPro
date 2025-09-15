package com.tournoipro.repository;

import com.tournoipro.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT COUNT(m) FROM Match m")
    long countMatchs();

    List<Match> findByTermine(Boolean termine);

    List<Match> findByDate(LocalDate date);

    @Query("SELECT m FROM Match m WHERE m.date >= :dateDebut AND m.date <= :dateFin ORDER BY m.date, m.heure")
    List<Match> findMatchsBetweenDates(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
    
    @Query("SELECT m FROM Match m WHERE m.date = :date AND m.heure = :heure AND m.terrain.id = :terrainId")
    List<Match> findConflictingMatches(@Param("date") LocalDate date, 
                                      @Param("heure") LocalTime heure, 
                                      @Param("terrainId") Long terrainId);
    
    @Query("SELECT m FROM Match m WHERE m.date = :date AND m.heure = :heure AND (m.equipe1.id = :equipe1Id OR m.equipe1.id = :equipe2Id OR m.equipe2.id = :equipe1Id OR m.equipe2.id = :equipe2Id)")
    List<Match> findTeamConflicts(@Param("date") LocalDate date, 
                                 @Param("heure") LocalTime heure, 
                                 @Param("equipe1Id") Long equipe1Id, 
                                 @Param("equipe2Id") Long equipe2Id);
}

