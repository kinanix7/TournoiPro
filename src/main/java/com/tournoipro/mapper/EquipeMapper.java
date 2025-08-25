package com.tournoipro.mapper;

import com.tournoipro.dto.EquipeDto;
import com.tournoipro.model.Equipe;
import com.tournoipro.model.Poule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {JoueurMapper.class})
public interface EquipeMapper {

    EquipeMapper INSTANCE = Mappers.getMapper(EquipeMapper.class);

    @Mapping(source = "poule.id", target = "pouleId")
    @Mapping(source = "poule.nom", target = "pouleNom")
    EquipeDto toDto(Equipe equipe);

    @Mapping(source = "equipeDto", target = "poule", qualifiedByName = "mapPoule")
    Equipe toEntity(EquipeDto equipeDto);

    List<EquipeDto> toDtoList(List<Equipe> equipes);
    List<Equipe> toEntityList(List<EquipeDto> equipeDtos);

    @Named("mapPoule")
    default Poule mapPoule(EquipeDto equipeDto) {
        if (equipeDto.getPouleId() == null) {
            return null;
        }
        Poule poule = new Poule();
        poule.setId(equipeDto.getPouleId());
        return poule;
    }
}