package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "joueurs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeJoueur type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id")
    @JsonIgnoreProperties({"joueurs", "matchsEquipe1", "matchsEquipe2", "classements", "arbitres", "poule"})
    private Equipe equipe;

    public Joueur() {}
    
    public Joueur(String nom, String role, TypeJoueur type, Equipe equipe) {
        this.nom = nom;
        this.role = role;
        this.type = type;
        this.equipe = equipe;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public TypeJoueur getType() { return type; }
    public void setType(TypeJoueur type) { this.type = type; }
    
    public Equipe getEquipe() { return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }
}

