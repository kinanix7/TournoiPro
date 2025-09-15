package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "arbitres")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Arbitre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_liee_id")
    @JsonIgnoreProperties({"arbitres", "joueurs", "matchsEquipe1", "matchsEquipe2", "classements", "poule"})
    private Equipe equipeLiee;

    @OneToMany(mappedBy = "arbitre", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"arbitre", "equipe1", "equipe2", "terrain", "poule"})
    private List<Match> matchs;

    public Arbitre() {}
    
    public Arbitre(String nom, Equipe equipeLiee, List<Match> matchs) {
        this.nom = nom;
        this.equipeLiee = equipeLiee;
        this.matchs = matchs;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public Equipe getEquipeLiee() { return equipeLiee; }
    public void setEquipeLiee(Equipe equipeLiee) { this.equipeLiee = equipeLiee; }
    
    public List<Match> getMatchs() { return matchs; }
    public void setMatchs(List<Match> matchs) { this.matchs = matchs; }
}
