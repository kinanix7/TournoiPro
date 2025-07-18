package com.tournoipro.repository;

import com.tournoipro.model.Poule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PouleRepository extends JpaRepository<Poule, Long> {
    
    Optional<Poule> findByNom(String nom);
    
    boolean existsByNom(String nom);
}

