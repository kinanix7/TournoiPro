package com.tournoipro.mapper;

import com.tournoipro.dto.MatchDto;
import com.tournoipro.model.Match;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {
    
    public MatchDto toDto(Match match) {
        if (match == null) {
            return null;
        }
        
        MatchDto dto = new MatchDto();
        dto.setId(match.getId());
        dto.setDate(match.getDate());
        dto.setHeure(match.getHeure());
        dto.setScoreEquipe1(match.getScoreEquipe1());
        dto.setScoreEquipe2(match.getScoreEquipe2());
        dto.setTermine(match.getTermine());
        
        if (match.getEquipe1() != null) {
            dto.setEquipe1Id(match.getEquipe1().getId());
            dto.setEquipe1Nom(match.getEquipe1().getNom());
        }
        
        if (match.getEquipe2() != null) {
            dto.setEquipe2Id(match.getEquipe2().getId());
            dto.setEquipe2Nom(match.getEquipe2().getNom());
        }
        
        if (match.getTerrain() != null) {
            dto.setTerrainId(match.getTerrain().getId());
            dto.setTerrainNom(match.getTerrain().getNom());
        }
        
        if (match.getArbitre() != null) {
            dto.setArbitreId(match.getArbitre().getId());
            dto.setArbitreNom(match.getArbitre().getNom());
        }
        
        if (match.getPoule() != null) {
            dto.setPouleId(match.getPoule().getId());
            dto.setPouleNom(match.getPoule().getNom());
        }
        
        return dto;
    }
}