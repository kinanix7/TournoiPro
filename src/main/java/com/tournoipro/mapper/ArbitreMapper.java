package com.tournoipro.mapper;

import com.tournoipro.dto.ArbitreDto;
import com.tournoipro.model.Arbitre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArbitreMapper {
    
    ArbitreMapper INSTANCE = Mappers.getMapper(ArbitreMapper.class);
    
    @Mapping(source = "equipeLiee.id", target = "equipeLieeId")
    @Mapping(source = "equipeLiee.nom", target = "equipeLieeNom")
    ArbitreDto toDto(Arbitre arbitre);
    
    @Mapping(source = "equipeLieeId", target = "equipeLiee.id")
    Arbitre toEntity(ArbitreDto arbitreDto);
    
    List<ArbitreDto> toDtoList(List<Arbitre> arbitres);
    List<Arbitre> toEntityList(List<ArbitreDto> arbitreDtos);
}

