package com.tournoipro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "equipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Joueur> joueurs;
    private Integer nbVictoires = 0;
    private Integer nbDefaites = 0;
    private Integer points = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poule_id")
    private Poule poule;
    
    @OneToMany(mappedBy = "equipe1", cascade = CascadeType.ALL)
    private List<Match> matchsEquipe1;
    
    @OneToMany(mappedBy = "equipe2", cascade = CascadeType.ALL)
    private List<Match> matchsEquipe2;
    
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    private List<Classement> classements;
}

