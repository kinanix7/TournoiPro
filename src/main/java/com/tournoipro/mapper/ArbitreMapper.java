package com.tournoipro.mapper;

import com.tournoipro.dto.ArbitreDto;
import com.tournoipro.model.Arbitre;
import com.tournoipro.model.Equipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArbitreMapper {

    ArbitreMapper INSTANCE = Mappers.getMapper(ArbitreMapper.class);

    @Mapping(source = "equipeLiee.id", target = "equipeLieeId")
    @Mapping(source = "equipeLiee.nom", target = "equipeLieeNom")
    ArbitreDto toDto(Arbitre arbitre);

    @Mapping(source = "arbitreDto", target = "equipeLiee", qualifiedByName = "mapEquipeLiee")
    Arbitre toEntity(ArbitreDto arbitreDto);

    List<ArbitreDto> toDtoList(List<Arbitre> arbitres);
    List<Arbitre> toEntityList(List<ArbitreDto> arbitreDtos);

    @Named("mapEquipeLiee")
    default Equipe mapEquipeLiee(ArbitreDto arbitreDto) {
        if (arbitreDto.getEquipeLieeId() == null) {
            return null;
        }
        Equipe equipe = new Equipe();
        equipe.setId(arbitreDto.getEquipeLieeId());
        return equipe;
    }
}