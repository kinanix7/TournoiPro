package com.tournoipro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}

