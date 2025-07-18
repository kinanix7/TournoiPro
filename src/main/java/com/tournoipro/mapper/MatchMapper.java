package com.tournoipro.mapper;

import com.tournoipro.dto.MatchDto;
import com.tournoipro.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    
    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);
    
    @Mapping(source = "equipe1.id", target = "equipe1Id")
    @Mapping(source = "equipe1.nom", target = "equipe1Nom")
    @Mapping(source = "equipe2.id", target = "equipe2Id")
    @Mapping(source = "equipe2.nom", target = "equipe2Nom")
    @Mapping(source = "terrain.id", target = "terrainId")
    @Mapping(source = "terrain.nom", target = "terrainNom")
    @Mapping(source = "arbitre.id", target = "arbitreId")
    @Mapping(source = "arbitre.nom", target = "arbitreNom")
    @Mapping(source = "poule.id", target = "pouleId")
    @Mapping(source = "poule.nom", target = "pouleNom")
    MatchDto toDto(Match match);
    
    @Mapping(source = "equipe1Id", target = "equipe1.id")
    @Mapping(source = "equipe2Id", target = "equipe2.id")
    @Mapping(source = "terrainId", target = "terrain.id")
    @Mapping(source = "arbitreId", target = "arbitre.id")
    @Mapping(source = "pouleId", target = "poule.id")
    Match toEntity(MatchDto matchDto);
    
    List<MatchDto> toDtoList(List<Match> matchs);
    List<Match> toEntityList(List<MatchDto> matchDtos);
}

