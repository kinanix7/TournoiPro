package com.tournoipro.repository;

import com.tournoipro.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    @Query("SELECT COUNT(e) FROM Equipe e")
    long countEquipes();

}

