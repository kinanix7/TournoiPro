package com.tournoipro.repository;

import com.tournoipro.model.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TerrainRepository extends JpaRepository<Terrain, Long> {
    
    Optional<Terrain> findByNom(String nom);
    
    List<Terrain> findByLocalisationContainingIgnoreCase(String localisation);
    
    @Query("SELECT t FROM Terrain t WHERE t.id NOT IN " +
           "(SELECT m.terrain.id FROM Match m WHERE m.date = :date AND m.heure = :heure)")
    List<Terrain> findTerrainsDisponibles(@Param("date") LocalDate date, @Param("heure") LocalTime heure);
    
    boolean existsByNom(String nom);
}

