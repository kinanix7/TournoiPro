package com.tournoipro.repository;

import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {

    @Query("SELECT COUNT(j) FROM Joueur j")
    long countJoueurs();

    List<Joueur> findByType(TypeJoueur type);

    //@Query(value = "SELECT j.* FROM joueurs j INNER JOIN equipes e ON j.equipe_id = e.id WHERE e.nom = :nom", nativeQuery = true)
    // List<Joueur> getJoueursByEquipeNom(String nom);

    List<Joueur> findByNomContainingIgnoreCase(String nom);

    @Query(value = "SELECT * FROM joueurs WHERE equipe_id = :equipeId", nativeQuery = true)
    List<Joueur> getJoueursByEquipeId(Long equipeId);
}

