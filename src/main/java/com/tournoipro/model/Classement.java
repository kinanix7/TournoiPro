package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "classements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = false)
    @JsonBackReference
    private Equipe equipe;

    @Column(nullable = false)
    private Integer points = 0;

    @Column(nullable = false)
    private Integer victoire = 0;

    @Column(nullable = false)
    private Integer defaite = 0;

    @Column(nullable = false)
    private Integer position = 0;
}

