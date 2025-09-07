package com.tournoipro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "arbitres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Arbitre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_liee_id")
    private Equipe equipeLiee;

    @OneToMany(mappedBy = "arbitre", cascade = CascadeType.ALL)
    private List<Match> matchs;
}

