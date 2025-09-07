package com.tournoipro.service;

import com.tournoipro.model.Joueur;
import com.tournoipro.repository.JoueurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoueurService {

    private final JoueurRepository joueurRepository;

    public JoueurService(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    public Joueur createJoueur(Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    public List<Joueur> getAllJoueurs() {
        return joueurRepository.findAll();
    }

    public Joueur getJoueurById(Long id) {
        return joueurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec id=" + id));
    }


    public Joueur updateJoueur(Long id, Joueur joueurDetails) {
        Joueur joueur = getJoueurById(id);
        joueur.setNom(joueurDetails.getNom());
        joueur.setRole(joueurDetails.getRole());
        joueur.setType(joueurDetails.getType());
        joueur.setEquipe(joueurDetails.getEquipe());
        return joueurRepository.save(joueur);
    }

    public void deleteJoueur(Long id) {
        Joueur joueur = getJoueurById(id);
        joueurRepository.delete(joueur);
    }
}
