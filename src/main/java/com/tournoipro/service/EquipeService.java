package com.tournoipro.service;

import com.tournoipro.model.Equipe;
import com.tournoipro.repository.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    public Equipe createEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll();
    }

    public Equipe getEquipeById(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe non trouvée avec id=" + id));
    }

    public Equipe updateEquipe(Long id, Equipe equipeDetails) {
        Equipe equipe = getEquipeById(id);
        equipe.setNom(equipeDetails.getNom());
        equipe.setJoueurs(equipeDetails.getJoueurs());
        equipe.setNbVictoires(equipeDetails.getNbVictoires());
        equipe.setNbDefaites(equipeDetails.getNbDefaites());
        equipe.setPoints(equipeDetails.getPoints());
        equipe.setPoule(equipeDetails.getPoule());
        equipe.setMatchsEquipe1(equipeDetails.getMatchsEquipe1());
        equipe.setMatchsEquipe2(equipeDetails.getMatchsEquipe2());
        equipe.setClassements(equipeDetails.getClassements());
        return equipeRepository.save(equipe);
    }

    public void deleteEquipe(Long id) {
        Equipe equipe = getEquipeById(id);
        equipeRepository.delete(equipe);
    }


    public long getEquipesCount() {
        return equipeRepository.countEquipes();
    }
}
