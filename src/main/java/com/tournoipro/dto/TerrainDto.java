package com.tournoipro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerrainDto {
    private Long id;
    private String nom;
    private String localisation;
}

