package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "poules")
public class Poule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "poule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("poule-equipes")
    private List<Equipe> equipes;

    @OneToMany(mappedBy = "poule", cascade = CascadeType.ALL)
    @JsonManagedReference("poule-matches")
    private List<Match> matchs;

    public Poule() {}
    
    public Poule(String nom, List<Equipe> equipes, List<Match> matchs) {
        this.nom = nom;
        this.equipes = equipes;
        this.matchs = matchs;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public List<Equipe> getEquipes() { return equipes; }
    public void setEquipes(List<Equipe> equipes) { this.equipes = equipes; }
    
    public List<Match> getMatchs() { return matchs; }
    public void setMatchs(List<Match> matchs) { this.matchs = matchs; }
}

