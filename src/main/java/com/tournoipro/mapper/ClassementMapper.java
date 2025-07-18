package com.tournoipro.mapper;

import com.tournoipro.dto.ClassementDto;
import com.tournoipro.model.Classement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassementMapper {
    
    ClassementMapper INSTANCE = Mappers.getMapper(ClassementMapper.class);
    
    @Mapping(source = "equipe.id", target = "equipeId")
    @Mapping(source = "equipe.nom", target = "equipeNom")
    ClassementDto toDto(Classement classement);
    
    @Mapping(source = "equipeId", target = "equipe.id")
    Classement toEntity(ClassementDto classementDto);
    
    List<ClassementDto> toDtoList(List<Classement> classements);
    List<Classement> toEntityList(List<ClassementDto> classementDtos);
}

