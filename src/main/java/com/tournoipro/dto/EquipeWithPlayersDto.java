package com.tournoipro.dto;

import java.util.List;


public class EquipeWithPlayersDto {
    private Long id;
    private String nom;
    private List<JoueurWithTeamDto> joueurs;
    private Integer nbVictoires;
    private Integer nbDefaites;
    private Integer points;
    private PouleBasicInfo poule;

    public EquipeWithPlayersDto() {}

    // Constructor for teams with poule
    public EquipeWithPlayersDto(Long id, String nom, List<JoueurWithTeamDto> joueurs, Integer nbVictoires, Integer nbDefaites, Integer points,
                               Long pouleId, String pouleNom) {
        this.id = id;
        this.nom = nom;
        this.joueurs = joueurs;
        this.nbVictoires = nbVictoires;
        this.nbDefaites = nbDefaites;
        this.points = points;
        if (pouleId != null && pouleNom != null) {
            this.poule = new PouleBasicInfo(pouleId, pouleNom);
        }
    }

    // Constructor for teams without poule
    public EquipeWithPlayersDto(Long id, String nom, List<JoueurWithTeamDto> joueurs, Integer nbVictoires, Integer nbDefaites, Integer points) {
        this.id = id;
        this.nom = nom;
        this.joueurs = joueurs;
        this.nbVictoires = nbVictoires;
        this.nbDefaites = nbDefaites;
        this.points = points;
        this.poule = null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public List<JoueurWithTeamDto> getJoueurs() { return joueurs; }
    public void setJoueurs(List<JoueurWithTeamDto> joueurs) { this.joueurs = joueurs; }

    public Integer getNbVictoires() { return nbVictoires; }
    public void setNbVictoires(Integer nbVictoires) { this.nbVictoires = nbVictoires; }

    public Integer getNbDefaites() { return nbDefaites; }
    public void setNbDefaites(Integer nbDefaites) { this.nbDefaites = nbDefaites; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public PouleBasicInfo getPoule() { return poule; }
    public void setPoule(PouleBasicInfo poule) { this.poule = poule; }

    public static class PouleBasicInfo {
        private Long id;
        private String nom;

        public PouleBasicInfo() {}

        public PouleBasicInfo(Long id, String nom) {
            this.id = id;
            this.nom = nom;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
    }
}