package com.tournoipro.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class MatchDto {
    private Long id;
    private LocalDate date;
    private LocalTime heure;
    private Long equipe1Id;
    private String equipe1Nom;
    private Long equipe2Id;
    private String equipe2Nom;
    private Long terrainId;
    private String terrainNom;
    private Long arbitreId;
    private String arbitreNom;
    private Integer scoreEquipe1;
    private Integer scoreEquipe2;
    private Long pouleId;
    private String pouleNom;
    private Boolean termine;
    
    public MatchDto() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public LocalTime getHeure() { return heure; }
    public void setHeure(LocalTime heure) { this.heure = heure; }
    
    public Long getEquipe1Id() { return equipe1Id; }
    public void setEquipe1Id(Long equipe1Id) { this.equipe1Id = equipe1Id; }
    
    public String getEquipe1Nom() { return equipe1Nom; }
    public void setEquipe1Nom(String equipe1Nom) { this.equipe1Nom = equipe1Nom; }
    
    public Long getEquipe2Id() { return equipe2Id; }
    public void setEquipe2Id(Long equipe2Id) { this.equipe2Id = equipe2Id; }
    
    public String getEquipe2Nom() { return equipe2Nom; }
    public void setEquipe2Nom(String equipe2Nom) { this.equipe2Nom = equipe2Nom; }
    
    public Long getTerrainId() { return terrainId; }
    public void setTerrainId(Long terrainId) { this.terrainId = terrainId; }
    
    public String getTerrainNom() { return terrainNom; }
    public void setTerrainNom(String terrainNom) { this.terrainNom = terrainNom; }
    
    public Long getArbitreId() { return arbitreId; }
    public void setArbitreId(Long arbitreId) { this.arbitreId = arbitreId; }
    
    public String getArbitreNom() { return arbitreNom; }
    public void setArbitreNom(String arbitreNom) { this.arbitreNom = arbitreNom; }
    
    public Integer getScoreEquipe1() { return scoreEquipe1; }
    public void setScoreEquipe1(Integer scoreEquipe1) { this.scoreEquipe1 = scoreEquipe1; }
    
    public Integer getScoreEquipe2() { return scoreEquipe2; }
    public void setScoreEquipe2(Integer scoreEquipe2) { this.scoreEquipe2 = scoreEquipe2; }
    
    public Long getPouleId() { return pouleId; }
    public void setPouleId(Long pouleId) { this.pouleId = pouleId; }
    
    public String getPouleNom() { return pouleNom; }
    public void setPouleNom(String pouleNom) { this.pouleNom = pouleNom; }
    
    public Boolean getTermine() { return termine; }
    public void setTermine(Boolean termine) { this.termine = termine; }
}