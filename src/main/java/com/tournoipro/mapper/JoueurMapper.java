package com.tournoipro.mapper;

import com.tournoipro.dto.JoueurDto;
import com.tournoipro.model.Joueur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JoueurMapper {
    
    JoueurMapper INSTANCE = Mappers.getMapper(JoueurMapper.class);
    
    @Mapping(source = "equipe.id", target = "equipeId")
    @Mapping(source = "equipe.nom", target = "equipeNom")
    JoueurDto toDto(Joueur joueur);
    
    @Mapping(source = "equipeId", target = "equipe.id")
    Joueur toEntity(JoueurDto joueurDto);
    
    List<JoueurDto> toDtoList(List<Joueur> joueurs);
    List<Joueur> toEntityList(List<JoueurDto> joueurDtos);
}

