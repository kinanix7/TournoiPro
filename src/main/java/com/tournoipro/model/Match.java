package com.tournoipro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "matchs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime heure;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe1_id", nullable = false)
    private Equipe equipe1;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe2_id", nullable = false)
    private Equipe equipe2;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terrain_id", nullable = false)
    private Terrain terrain;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arbitre_id")
    private Arbitre arbitre;
    private Integer scoreEquipe1;
    private Integer scoreEquipe2;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poule_id", nullable = false)
    private Poule poule;
    private Boolean termine = false;
}

