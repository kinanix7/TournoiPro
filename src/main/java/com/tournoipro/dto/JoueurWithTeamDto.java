package com.tournoipro.dto;

import com.tournoipro.model.TypeJoueur;


public class JoueurWithTeamDto {
    private Long id;
    private String nom;
    private String role;
    private TypeJoueur type;
    private TeamBasicInfo equipe;

    public JoueurWithTeamDto() {}

    public JoueurWithTeamDto(Long id, String nom, String role, TypeJoueur type, Long equipeId, String equipeNom) {
        this.id = id;
        this.nom = nom;
        this.role = role;
        this.type = type;
        if (equipeId != null && equipeNom != null) {
            this.equipe = new TeamBasicInfo(equipeId, equipeNom);
        }
    }

    public JoueurWithTeamDto(Long id, String nom, String role, TypeJoueur type) {
        this.id = id;
        this.nom = nom;
        this.role = role;
        this.type = type;
        this.equipe = null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public TypeJoueur getType() { return type; }
    public void setType(TypeJoueur type) { this.type = type; }

    public TeamBasicInfo getEquipe() { return equipe; }
    public void setEquipe(TeamBasicInfo equipe) { this.equipe = equipe; }

    public static class TeamBasicInfo {
        private Long id;
        private String nom;

        public TeamBasicInfo() {}

        public TeamBasicInfo(Long id, String nom) {
            this.id = id;
            this.nom = nom;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
    }
}