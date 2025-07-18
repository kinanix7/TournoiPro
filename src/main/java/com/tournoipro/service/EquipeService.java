package com.tournoipro.service;

import com.tournoipro.dto.EquipeDto;
import com.tournoipro.mapper.EquipeMapper;
import com.tournoipro.model.Equipe;
import com.tournoipro.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private EquipeMapper equipeMapper;

    public List<EquipeDto> getAllEquipes() {
        List<Equipe> equipes = equipeRepository.findAll();
        return equipeMapper.toDtoList(equipes);
    }

    public Optional<EquipeDto> getEquipeById(Long id) {
        return equipeRepository.findById(id)
                .map(equipeMapper::toDto);
    }

    public Optional<EquipeDto> getEquipeByNom(String nom) {
        return equipeRepository.findByNom(nom)
                .map(equipeMapper::toDto);
    }

    public List<EquipeDto> getEquipesByPoule(Long pouleId) {
        List<Equipe> equipes = equipeRepository.findByPouleId(pouleId);
        return equipeMapper.toDtoList(equipes);
    }

    public List<EquipeDto> getEquipesSansPoule() {
        List<Equipe> equipes = equipeRepository.findEquipesSansPoule();
        return equipeMapper.toDtoList(equipes);
    }

    public EquipeDto createEquipe(EquipeDto equipeDto) {
        if (equipeRepository.existsByNom(equipeDto.getNom())) {
            throw new RuntimeException("Une équipe avec ce nom existe déjà");
        }

        Equipe equipe = equipeMapper.toEntity(equipeDto);
        equipe.setNbVictoires(0);
        equipe.setNbDefaites(0);
        equipe.setPoints(0);

        Equipe savedEquipe = equipeRepository.save(equipe);
        return equipeMapper.toDto(savedEquipe);
    }

    public EquipeDto updateEquipe(Long id, EquipeDto equipeDto) {
        return equipeRepository.findById(id)
                .map(existingEquipe -> {
                    if (!existingEquipe.getNom().equals(equipeDto.getNom()) &&
                            equipeRepository.existsByNom(equipeDto.getNom())) {
                        throw new RuntimeException("Une équipe avec ce nom existe déjà");
                    }

                    existingEquipe.setNom(equipeDto.getNom());
                    // Ne pas modifier les statistiques via cette méthode

                    Equipe updatedEquipe = equipeRepository.save(existingEquipe);
                    return equipeMapper.toDto(updatedEquipe);
                })
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée avec l'id: " + id));
    }

    public void deleteEquipe(Long id) {
        if (!equipeRepository.existsById(id)) {
            throw new RuntimeException("Équipe non trouvée avec l'id: " + id);
        }
        equipeRepository.deleteById(id);
    }

    public void updateStatistiques(Long equipeId, boolean victoire) {
        Equipe equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Équipe non trouvée"));

        if (victoire) {
            equipe.setNbVictoires(equipe.getNbVictoires() + 1);
            equipe.setPoints(equipe.getPoints() + 3); // 3 points pour une victoire
        } else {
            equipe.setNbDefaites(equipe.getNbDefaites() + 1);
            equipe.setPoints(equipe.getPoints() + 1); // 1 point pour une défaite
        }

        equipeRepository.save(equipe);
    }
}

