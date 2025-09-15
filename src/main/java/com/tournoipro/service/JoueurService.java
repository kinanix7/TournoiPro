package com.tournoipro.service;

import com.tournoipro.dto.JoueurWithTeamDto;
import com.tournoipro.model.Equipe;
import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
import com.tournoipro.repository.EquipeRepository;
import com.tournoipro.repository.JoueurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoueurService {

    private final JoueurRepository joueurRepository;
    private final EquipeRepository equipeRepository;

    public JoueurService(JoueurRepository joueurRepository, EquipeRepository equipeRepository) {
        this.joueurRepository = joueurRepository;
        this.equipeRepository = equipeRepository;
    }

    public Joueur createJoueur(Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    public List<Joueur> getAllJoueurs() {
        return joueurRepository.findAll();
    }


    public List<JoueurWithTeamDto> getAllJoueursWithTeamInfo() {
        return joueurRepository.findAllJoueursWithTeamInfo();
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


    public Joueur assignPlayerToTeam(Long playerId, Long equipeId) {
        Joueur joueur = getJoueurById(playerId);
        Equipe equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Team not found with id=" + equipeId));
        
        // Check if player is already assigned to another team
        if (joueur.getEquipe() != null && !joueur.getEquipe().getId().equals(equipeId)) {
            throw new IllegalArgumentException("Player is already assigned to team: " + joueur.getEquipe().getNom());
        }
        
        // Check team capacity (max 8 players)
        List<Joueur> currentPlayers = getJoueursByEquipeId(equipeId);
        if (currentPlayers.size() >= 8) {
            throw new IllegalArgumentException("Team is full (maximum 8 players)");
        }
        
        // Assign player to team
        joueur.setEquipe(equipe);
        return joueurRepository.save(joueur);
    }


    public Joueur unassignPlayerFromTeam(Long playerId) {
        Joueur joueur = getJoueurById(playerId);
        
        if (joueur.getEquipe() == null) {
            throw new IllegalArgumentException("Player is not assigned to any team");
        }
        
        // Unassign player from team
        joueur.setEquipe(null);
        return joueurRepository.save(joueur);
    }


    public List<Joueur> getUnassignedPlayers() {
        return joueurRepository.findUnassignedPlayers();
    }
}
