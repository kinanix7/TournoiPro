package com.tournoipro.mapper;

import com.tournoipro.dto.PouleDto;
import com.tournoipro.model.Poule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EquipeMapper.class})
public interface PouleMapper {
    
    PouleMapper INSTANCE = Mappers.getMapper(PouleMapper.class);
    
    PouleDto toDto(Poule poule);
    Poule toEntity(PouleDto pouleDto);
    
    List<PouleDto> toDtoList(List<Poule> poules);
    List<Poule> toEntityList(List<PouleDto> pouleDtos);
}

