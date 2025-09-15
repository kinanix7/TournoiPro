package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "equipes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"equipe", "poule"})
    private List<Joueur> joueurs;

    @Column(nullable = false)
    private Integer nbVictoires = 0;

    @Column(nullable = false)
    private Integer nbDefaites = 0;

    @Column(nullable = false)
    private Integer points = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poule_id")
    @JsonBackReference("poule-equipes")
    @JsonIgnoreProperties({"equipes", "matchs"})
    private Poule poule;

    @OneToMany(mappedBy = "equipe1", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"equipe1", "equipe2", "terrain", "arbitre", "poule"})
    private List<Match> matchsEquipe1;

    @OneToMany(mappedBy = "equipe2", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"equipe1", "equipe2", "terrain", "arbitre", "poule"})
    private List<Match> matchsEquipe2;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"equipe"})
    private List<Classement> classements;

    @OneToMany(mappedBy = "equipeLiee", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"equipeLiee"})
    private List<Arbitre> arbitres;

    public Equipe() {}
    
    public Equipe(String nom, List<Joueur> joueurs, Integer nbVictoires, Integer nbDefaites, Integer points, Poule poule, List<Match> matchsEquipe1, List<Match> matchsEquipe2, List<Classement> classements, List<Arbitre> arbitres) {
        this.nom = nom;
        this.joueurs = joueurs;
        this.nbVictoires = nbVictoires;
        this.nbDefaites = nbDefaites;
        this.points = points;
        this.poule = poule;
        this.matchsEquipe1 = matchsEquipe1;
        this.matchsEquipe2 = matchsEquipe2;
        this.classements = classements;
        this.arbitres = arbitres;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public List<Joueur> getJoueurs() { return joueurs; }
    public void setJoueurs(List<Joueur> joueurs) { this.joueurs = joueurs; }
    
    public Integer getNbVictoires() { return nbVictoires; }
    public void setNbVictoires(Integer nbVictoires) { this.nbVictoires = nbVictoires; }
    
    public Integer getNbDefaites() { return nbDefaites; }
    public void setNbDefaites(Integer nbDefaites) { this.nbDefaites = nbDefaites; }
    
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    
    public Poule getPoule() { return poule; }
    public void setPoule(Poule poule) { this.poule = poule; }
    
    public List<Match> getMatchsEquipe1() { return matchsEquipe1; }
    public void setMatchsEquipe1(List<Match> matchsEquipe1) { this.matchsEquipe1 = matchsEquipe1; }
    
    public List<Match> getMatchsEquipe2() { return matchsEquipe2; }
    public void setMatchsEquipe2(List<Match> matchsEquipe2) { this.matchsEquipe2 = matchsEquipe2; }
    
    public List<Classement> getClassements() { return classements; }
    public void setClassements(List<Classement> classements) { this.classements = classements; }
    
    public List<Arbitre> getArbitres() { return arbitres; }
    public void setArbitres(List<Arbitre> arbitres) { this.arbitres = arbitres; }
}