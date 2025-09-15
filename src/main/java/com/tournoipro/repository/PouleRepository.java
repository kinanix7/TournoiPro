package com.tournoipro.repository;

import com.tournoipro.model.Poule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PouleRepository extends JpaRepository<Poule, Long> {
    @Query("SELECT p FROM Poule p WHERE p.nom = ?1")
    Poule findByNom(String nom);
}