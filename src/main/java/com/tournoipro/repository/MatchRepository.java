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

    @Query("SELECT COUNT(m) FROM Match m")
    long countMatchs();

    List<Match> findByTermine(Boolean termine);

    List<Match> findByDate(LocalDate date);

    @Query("SELECT m FROM Match m WHERE m.date >= :dateDebut AND m.date <= :dateFin ORDER BY m.date, m.heure")
    List<Match> findMatchsBetweenDates(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);
}

