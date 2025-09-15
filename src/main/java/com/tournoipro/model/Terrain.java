package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "terrains")
public class Terrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private String localisation;

    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    @JsonManagedReference("terrain-matches")
    private List<Match> matchs;

    public Terrain() {}
    
    public Terrain(String nom, String localisation, List<Match> matchs) {
        this.nom = nom;
        this.localisation = localisation;
        this.matchs = matchs;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
    
    public List<Match> getMatchs() { return matchs; }
    public void setMatchs(List<Match> matchs) { this.matchs = matchs; }
}

