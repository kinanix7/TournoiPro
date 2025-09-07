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

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Joueur> joueurs;

    @Column(nullable = false)
    private Integer nbVictoires = 0;

    @Column(nullable = false)
    private Integer nbDefaites = 0;

    @Column(nullable = false)
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

