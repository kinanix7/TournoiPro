package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "matchs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime heure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipe1_id", nullable = false)
    @JsonIgnoreProperties({"joueurs", "matchs", "poule"})
    private Equipe equipe1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "equipe2_id", nullable = false)
    @JsonIgnoreProperties({"joueurs", "matchs", "poule"})
    private Equipe equipe2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "terrain_id", nullable = false)
    @JsonBackReference("terrain-matches")
    @JsonIgnoreProperties({"matchs"})
    private Terrain terrain;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "arbitre_id")
    @JsonIgnoreProperties({"matchs", "equipeLiee"})
    private Arbitre arbitre;

    @Column
    private Integer scoreEquipe1;

    @Column
    private Integer scoreEquipe2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poule_id")
    @JsonBackReference("poule-matches")
    @JsonIgnoreProperties({"matchs", "equipes"})
    private Poule poule;

    @Column(nullable = false)
    private Boolean termine = false;

    public Match() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    
    public Equipe getEquipe1() { return equipe1; }
    public void setEquipe1(Equipe equipe1) { this.equipe1 = equipe1; }
    
    public Equipe getEquipe2() { return equipe2; }
    public void setEquipe2(Equipe equipe2) { this.equipe2 = equipe2; }
    
    public Terrain getTerrain() { return terrain; }
    public void setTerrain(Terrain terrain) { this.terrain = terrain; }
    
    public Arbitre getArbitre() { return arbitre; }
    public void setArbitre(Arbitre arbitre) { this.arbitre = arbitre; }
    
    public Integer getScoreEquipe1() { return scoreEquipe1; }
    public void setScoreEquipe1(Integer scoreEquipe1) { this.scoreEquipe1 = scoreEquipe1; }
    
    public Integer getScoreEquipe2() { return scoreEquipe2; }
    public void setScoreEquipe2(Integer scoreEquipe2) { this.scoreEquipe2 = scoreEquipe2; }
    
    public Poule getPoule() { return poule; }
    public void setPoule(Poule poule) { this.poule = poule; }
    
    public Boolean getTermine() { return termine; }
    public void setTermine(Boolean termine) { this.termine = termine; }
}