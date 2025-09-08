package com.tournoipro.repository;

import com.tournoipro.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT COUNT(m) FROM Match m")
    long countMatchs();

    List<Match> findByTermine(Boolean termine);
}

