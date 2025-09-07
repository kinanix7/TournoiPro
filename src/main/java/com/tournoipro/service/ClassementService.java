package com.tournoipro.service;

import com.tournoipro.model.Classement;
import com.tournoipro.repository.ClassementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassementService {

    private final ClassementRepository classementRepository;

    public ClassementService(ClassementRepository classementRepository) {
        this.classementRepository = classementRepository;
    }

    public Classement createClassement(Classement classement) {
        return classementRepository.save(classement);
    }

    public List<Classement> getAllClassements() {
        return classementRepository.findAll();
    }

    public Classement getClassementById(Long id) {
        return classementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classement non trouvé avec id=" + id));
    }

    public Classement updateClassement(Long id, Classement classementDetails) {
        Classement classement = getClassementById(id);
        classement.setEquipe(classementDetails.getEquipe());
        classement.setPoints(classementDetails.getPoints());
        classement.setVictoire(classementDetails.getVictoire());
        classement.setDefaite(classementDetails.getDefaite());
        classement.setPosition(classementDetails.getPosition());
        return classementRepository.save(classement);
    }

    public void deleteClassement(Long id) {
        Classement classement = getClassementById(id);
        classementRepository.delete(classement);
    }
}
