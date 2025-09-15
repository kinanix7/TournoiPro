package com.tournoipro.dto;

public class ArbitreWithTeamDto {
    private Long id;
    private String nom;
    private TeamBasicInfo equipeLiee;

    public ArbitreWithTeamDto() {}

    public ArbitreWithTeamDto(Long id, String nom, Long equipeId, String equipeNom) {
        this.id = id;
        this.nom = nom;
        if (equipeId != null && equipeNom != null) {
            this.equipeLiee = new TeamBasicInfo(equipeId, equipeNom);
        }
    }

    public ArbitreWithTeamDto(Long id, String nom) {
        this.id = id;
        this.nom = nom;
        this.equipeLiee = null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public TeamBasicInfo getEquipeLiee() { return equipeLiee; }
    public void setEquipeLiee(TeamBasicInfo equipeLiee) { this.equipeLiee = equipeLiee; }

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