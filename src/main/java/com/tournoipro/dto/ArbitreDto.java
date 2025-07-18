package com.tournoipro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArbitreDto {
    private Long id;
    private String nom;
    private Long equipeLieeId;
    private String equipeLieeNom;
}

