package com.tournoipro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "poules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "poule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipe> equipes;

    @OneToMany(mappedBy = "poule", cascade = CascadeType.ALL)
    private List<Match> matchs;
}

