package com.tournoipro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassementDto {
    private Long id;
    private Long equipeId;
    private String equipeNom;
    private Integer points;
    private Integer victoire;
    private Integer defaite;
    private Integer position;
}

