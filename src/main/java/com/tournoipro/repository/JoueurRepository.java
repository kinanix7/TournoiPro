package com.tournoipro.repository;

import com.tournoipro.model.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {

}

