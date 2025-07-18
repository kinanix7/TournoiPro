package com.tournoipro.mapper;

import com.tournoipro.dto.TerrainDto;
import com.tournoipro.model.Terrain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TerrainMapper {
    
    TerrainMapper INSTANCE = Mappers.getMapper(TerrainMapper.class);
    
    TerrainDto toDto(Terrain terrain);
    Terrain toEntity(TerrainDto terrainDto);
    
    List<TerrainDto> toDtoList(List<Terrain> terrains);
    List<Terrain> toEntityList(List<TerrainDto> terrainDtos);
}

