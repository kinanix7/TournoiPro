package com.tournoipro.service;

import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
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

    public long getJoueursCount() {
        return joueurRepository.countJoueurs();
    }

    public List<Joueur> getJoueurByType(TypeJoueur type) {
        return joueurRepository.findByType(type);

    }

    public List<Joueur> searchJoueursByNom(String nom) {
        return joueurRepository.findByNomContainingIgnoreCase(nom);
    }

    public List<Joueur> getJoueursByEquipeId(Long equipeId) {
        return joueurRepository.getJoueursByEquipeId(equipeId);
    }
}
