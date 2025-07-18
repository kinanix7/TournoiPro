package com.tournoipro.dto;

import com.tournoipro.model.TypeJoueur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoueurDto {
    private Long id;
    private String nom;
    private String role;
    private TypeJoueur type;
    private Long equipeId;
    private String equipeNom;
}

