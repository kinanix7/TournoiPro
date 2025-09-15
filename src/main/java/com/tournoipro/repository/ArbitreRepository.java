package com.tournoipro.repository;

import com.tournoipro.dto.ArbitreWithTeamDto;
import com.tournoipro.model.Arbitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArbitreRepository extends JpaRepository<Arbitre, Long> {

    @Query("SELECT COUNT(a) FROM Arbitre a")
    long countArbitres();


    @Query("SELECT new com.tournoipro.dto.ArbitreWithTeamDto(a.id, a.nom, e.id, e.nom) " +
           "FROM Arbitre a LEFT JOIN a.equipeLiee e")
    List<ArbitreWithTeamDto> findAllArbitresWithTeamInfo();
}

