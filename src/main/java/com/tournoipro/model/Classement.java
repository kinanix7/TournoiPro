package com.tournoipro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "classements")
public class Classement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = false)
    @JsonBackReference("equipe-classements")
    private Equipe equipe;

    @Column(nullable = false)
    private Integer points = 0;

    @Column(nullable = false)
    private Integer victoire = 0;

    @Column(nullable = false)
    private Integer defaite = 0;

    @Column(nullable = false)
    private Integer position = 0;

    public Classement() {}
    
    public Classement(Equipe equipe, Integer points, Integer victoire, Integer defaite, Integer position) {
        this.equipe = equipe;
        this.points = points;
        this.victoire = victoire;
        this.defaite = defaite;
        this.position = position;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Equipe getEquipe() { return equipe; }
    public void setEquipe(Equipe equipe) { this.equipe = equipe; }
    
    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }
    
    public Integer getVictoire() { return victoire; }
    public void setVictoire(Integer victoire) { this.victoire = victoire; }
    
    public Integer getDefaite() { return defaite; }
    public void setDefaite(Integer defaite) { this.defaite = defaite; }
    
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
}

