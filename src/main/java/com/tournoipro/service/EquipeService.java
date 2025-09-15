package com.tournoipro.service;

import com.tournoipro.dto.EquipeWithPlayersDto;
import com.tournoipro.dto.JoueurWithTeamDto;
import com.tournoipro.model.Arbitre;
import com.tournoipro.model.Equipe;
import com.tournoipro.model.Joueur;
import com.tournoipro.repository.ArbitreRepository;
import com.tournoipro.repository.EquipeRepository;
import com.tournoipro.repository.JoueurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final JoueurRepository joueurRepository;
    private final ArbitreRepository arbitreRepository;

    public EquipeService(EquipeRepository equipeRepository, JoueurRepository joueurRepository, ArbitreRepository arbitreRepository) {
        this.equipeRepository = equipeRepository;
        this.joueurRepository = joueurRepository;
        this.arbitreRepository = arbitreRepository;
    }

    public Equipe createEquipe(Equipe equipe) {
        // Ensure the entity is treated as new by setting id to null
        equipe.setId(null);
        
        // Validate required fields
        if (equipe.getNom() == null || equipe.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        
        // Initialize default values if not provided
        if (equipe.getNbVictoires() == null) equipe.setNbVictoires(0);
        if (equipe.getNbDefaites() == null) equipe.setNbDefaites(0);
        if (equipe.getPoints() == null) equipe.setPoints(0);
        
        return equipeRepository.save(equipe);
    }

    public List<Equipe> getAllEquipes() {
        return equipeRepository.findAll();
    }


    public List<EquipeWithPlayersDto> getAllEquipesWithBasicInfo() {
        // Get teams with players and poule
        List<Equipe> teamsWithPlayersAndPoule = equipeRepository.findAllEquipesWithPlayersAndPoule();
        // Get teams with players without poule
        List<Equipe> teamsWithPlayersWithoutPoule = equipeRepository.findAllEquipesWithPlayersWithoutPoule();
        
        // Combine both lists
        teamsWithPlayersAndPoule.addAll(teamsWithPlayersWithoutPoule);
        
        // Convert to DTOs
        return teamsWithPlayersAndPoule.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Equipe getEquipeById(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe non trouvée avec id=" + id));
    }

    public List<Equipe> getEquipesByName(String nom) {
        return equipeRepository.findByNom(nom);
    }

    public Equipe updateEquipe(Long id, Equipe equipeDetails) {
        // Validate that the path id matches the entity id (if provided)
        if (equipeDetails.getId() != null && !equipeDetails.getId().equals(id)) {
            throw new IllegalArgumentException("Path ID does not match entity ID");
        }
        
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

    public List<Equipe> getEquipesByPoule(Long pouleId) {
        return equipeRepository.findByPouleId(pouleId);
    }

    public List<Equipe> getEquipesSansPoule() {
        return equipeRepository.findEquipesSansPoule();
    }

    /**
     * Add a player to a team
     * @param equipeId Team ID
     * @param playerId Player ID
     * @return Updated team
     */
    public Equipe addPlayerToTeam(Long equipeId, Long playerId) {
        Equipe equipe = getEquipeById(equipeId);
        Joueur joueur = joueurRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with id=" + playerId));
        
        // Check if player is already assigned to this team
        if (joueur.getEquipe() != null && joueur.getEquipe().getId().equals(equipeId)) {
            throw new IllegalArgumentException("Player is already assigned to this team");
        }
        
        // Check if player is already assigned to another team
        if (joueur.getEquipe() != null) {
            throw new IllegalArgumentException("Player is already assigned to team: " + joueur.getEquipe().getNom());
        }
        
        // Check team capacity using direct database query (more reliable than lazy collection)
        List<Joueur> currentPlayers = joueurRepository.getJoueursByEquipeId(equipeId);
        if (currentPlayers.size() >= 8) {
            throw new IllegalArgumentException("Team is full (maximum 8 players)");
        }
        
        // Set the bidirectional relationship
        joueur.setEquipe(equipe);
        
        // Initialize joueurs list if null
        if (equipe.getJoueurs() == null) {
            equipe.setJoueurs(new java.util.ArrayList<>());
        }
        
        // Add to collection if not already present
        if (!equipe.getJoueurs().contains(joueur)) {
            equipe.getJoueurs().add(joueur);
        }
        
        // Save both entities to maintain consistency
        joueurRepository.save(joueur);
        return equipeRepository.save(equipe);
    }


    public Equipe removePlayerFromTeam(Long equipeId, Long playerId) {
        Equipe equipe = getEquipeById(equipeId);
        Joueur joueur = joueurRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with id=" + playerId));
        
        // Check if player is actually assigned to this team
        if (joueur.getEquipe() == null || !joueur.getEquipe().getId().equals(equipeId)) {
            throw new IllegalArgumentException("Player is not assigned to this team");
        }
        
        // Remove the bidirectional relationship
        joueur.setEquipe(null);
        if (equipe.getJoueurs() != null) {
            equipe.getJoueurs().removeIf(p -> p.getId().equals(playerId));
        }
        
        // Save both entities to maintain consistency
        joueurRepository.save(joueur);
        return equipeRepository.save(equipe);
    }

    public Equipe addRefereeToTeam(Long equipeId, Long arbitreId) {
        Equipe equipe = getEquipeById(equipeId);
        Arbitre arbitre = arbitreRepository.findById(arbitreId)
                .orElseThrow(() -> new RuntimeException("Referee not found with id=" + arbitreId));
        
        // Check if referee is already assigned to another team
        if (arbitre.getEquipeLiee() != null) {
            throw new IllegalArgumentException("Referee is already assigned to team: " + arbitre.getEquipeLiee().getNom());
        }
        
        // Set the bidirectional relationship
        arbitre.setEquipeLiee(equipe);
        if (equipe.getArbitres() != null && !equipe.getArbitres().contains(arbitre)) {
            equipe.getArbitres().add(arbitre);
        }
        
        // Save both entities to maintain consistency
        arbitreRepository.save(arbitre);
        return equipeRepository.save(equipe);
    }


    public Equipe removeRefereeFromTeam(Long equipeId, Long arbitreId) {
        Equipe equipe = getEquipeById(equipeId);
        Arbitre arbitre = arbitreRepository.findById(arbitreId)
                .orElseThrow(() -> new RuntimeException("Referee not found with id=" + arbitreId));
        
        // Check if referee is actually assigned to this team
        if (arbitre.getEquipeLiee() == null || !arbitre.getEquipeLiee().getId().equals(equipeId)) {
            throw new IllegalArgumentException("Referee is not assigned to this team");
        }
        
        // Remove the bidirectional relationship
        arbitre.setEquipeLiee(null);
        if (equipe.getArbitres() != null) {
            equipe.getArbitres().removeIf(a -> a.getId().equals(arbitreId));
        }
        
        // Save both entities to maintain consistency
        arbitreRepository.save(arbitre);
        return equipeRepository.save(equipe);
    }

    private EquipeWithPlayersDto convertToDto(Equipe equipe) {
        // Convert players to DTOs
        List<JoueurWithTeamDto> joueurDtos = equipe.getJoueurs().stream()
            .map(joueur -> new JoueurWithTeamDto(
                joueur.getId(),
                joueur.getNom(),
                joueur.getRole(),
                joueur.getType(),
                joueur.getEquipe() != null ? joueur.getEquipe().getId() : null,
                joueur.getEquipe() != null ? joueur.getEquipe().getNom() : null
            ))
            .collect(Collectors.toList());
        
        // Create the DTO based on whether the equipe has a poule or not
        if (equipe.getPoule() != null) {
            return new EquipeWithPlayersDto(
                equipe.getId(),
                equipe.getNom(),
                joueurDtos,
                equipe.getNbVictoires(),
                equipe.getNbDefaites(),
                equipe.getPoints(),
                equipe.getPoule().getId(),
                equipe.getPoule().getNom()
            );
        } else {
            return new EquipeWithPlayersDto(
                equipe.getId(),
                equipe.getNom(),
                joueurDtos,
                equipe.getNbVictoires(),
                equipe.getNbDefaites(),
                equipe.getPoints()
            );
        }
    }
}