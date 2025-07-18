package com.tournoipro.repository;

import com.tournoipro.model.Arbitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ArbitreRepository extends JpaRepository<Arbitre, Long> {
    
    List<Arbitre> findByEquipeLieeId(Long equipeId);
    
    @Query("SELECT a FROM Arbitre a WHERE a.id NOT IN " +
           "(SELECT m.arbitre.id FROM Match m WHERE m.date = :date AND m.heure = :heure AND m.arbitre IS NOT NULL)")
    List<Arbitre> findArbitresDisponibles(@Param("date") LocalDate date, @Param("heure") LocalTime heure);
    
    boolean existsByNom(String nom);
}

