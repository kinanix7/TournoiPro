package com.tournoipro.service;

import com.tournoipro.model.Arbitre;
import com.tournoipro.repository.ArbitreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArbitreService {

    private final ArbitreRepository arbitreRepository;

    public ArbitreService(ArbitreRepository arbitreRepository) {
        this.arbitreRepository = arbitreRepository;
    }

    public Arbitre createArbitre(Arbitre arbitre) {
        return arbitreRepository.save(arbitre);
    }

    public List<Arbitre> getAllArbitres() {
        return arbitreRepository.findAll();
    }

    public Arbitre getArbitreById(Long id) {
        return arbitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbitre non trouvé avec id " + id));
    }

    public Arbitre updateArbitre(Long id, Arbitre arbitre) {
        Arbitre existingArbitre = arbitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbitre non trouvé avec id " + id));

        existingArbitre.setNom(arbitre.getNom());
        existingArbitre.setEquipeLiee(arbitre.getEquipeLiee());
        existingArbitre.setMatchs(arbitre.getMatchs());

        return arbitreRepository.save(existingArbitre);
    }

    public void deleteArbitre(Long id) {
        Arbitre arbitre = arbitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbitre non trouvé avec id " + id));
        arbitreRepository.delete(arbitre);
    }

    public long getArbitresCount() {
        return arbitreRepository.countArbitres();
    }
}
