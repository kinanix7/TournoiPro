package com.tournoipro.model;

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
    private Equipe equipe;
    private Integer points = 0;
    private Integer victoire = 0;
    private Integer defaite = 0;
    private Integer position = 0;
}

