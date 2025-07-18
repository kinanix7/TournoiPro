package com.tournoipro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDto {
    private Long id;
    private String nom;
    private List<JoueurDto> joueurs;
    private Integer nbVictoires;
    private Integer nbDefaites;
    private Integer points;
    private Long pouleId;
    private String pouleNom;
}

