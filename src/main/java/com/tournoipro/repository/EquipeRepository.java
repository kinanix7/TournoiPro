package com.tournoipro.repository;

import com.tournoipro.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    @Query("SELECT COUNT(e) FROM Equipe e")
    long countEquipes();

    List<Equipe> findByNom(String nom);

    List<Equipe> findByPouleId(Long pouleId);

    @Query("SELECT e FROM Equipe e WHERE e.poule IS NULL")
    List<Equipe> findEquipesSansPoule();

}

