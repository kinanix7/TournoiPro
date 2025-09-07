package com.tournoipro.repository;

import com.tournoipro.model.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {

    @Query("SELECT COUNT(j) FROM Joueur j")
    long countJoueurs();
}

