package com.tournoipro.service;

import com.tournoipro.dto.ArbitreWithTeamDto;
import com.tournoipro.model.Arbitre;
import com.tournoipro.model.Equipe;
import com.tournoipro.repository.ArbitreRepository;
import com.tournoipro.repository.EquipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArbitreService {

    private final ArbitreRepository arbitreRepository;
    private final EquipeRepository equipeRepository;

    public ArbitreService(ArbitreRepository arbitreRepository, EquipeRepository equipeRepository) {
        this.arbitreRepository = arbitreRepository;
        this.equipeRepository = equipeRepository;
    }

    public Arbitre createArbitre(Arbitre arbitre) {
        // Handle team relationship if team is assigned
        if (arbitre.getEquipeLiee() != null) {
            Equipe team = equipeRepository.findById(arbitre.getEquipeLiee().getId())
                    .orElseThrow(() -> new RuntimeException("Team not found with id=" + arbitre.getEquipeLiee().getId()));
            arbitre.setEquipeLiee(team);
            
            // Add referee to team's referee list
            if (team.getArbitres() != null && !team.getArbitres().contains(arbitre)) {
                team.getArbitres().add(arbitre);
                equipeRepository.save(team);
            }
        }
        return arbitreRepository.save(arbitre);
    }

    public List<Arbitre> getAllArbitres() {
        return arbitreRepository.findAll();
    }

    public List<ArbitreWithTeamDto> getAllArbitresWithTeamInfo() {
        return arbitreRepository.findAllArbitresWithTeamInfo();
    }

    public Arbitre getArbitreById(Long id) {
        return arbitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbitre non trouvé avec id " + id));
    }

    public Arbitre updateArbitre(Long id, Arbitre arbitre) {
        Arbitre existingArbitre = arbitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbitre non trouvé avec id " + id));

        // Handle team relationship changes
        Equipe oldTeam = existingArbitre.getEquipeLiee();
        Equipe newTeam = arbitre.getEquipeLiee();

        // Remove referee from old team's referee list
        if (oldTeam != null && !oldTeam.equals(newTeam)) {
            if (oldTeam.getArbitres() != null) {
                oldTeam.getArbitres().remove(existingArbitre);
                equipeRepository.save(oldTeam);
            }
        }

        // Add referee to new team's referee list
        if (newTeam != null) {
            Equipe team = equipeRepository.findById(newTeam.getId())
                    .orElseThrow(() -> new RuntimeException("Team not found with id=" + newTeam.getId()));
            existingArbitre.setEquipeLiee(team);
            
            if (team.getArbitres() != null && !team.getArbitres().contains(existingArbitre)) {
                team.getArbitres().add(existingArbitre);
                equipeRepository.save(team);
            }
        } else {
            existingArbitre.setEquipeLiee(null);
        }

        existingArbitre.setNom(arbitre.getNom());
        
        // Don't update matches as they should be managed separately
        // existingArbitre.setMatchs(arbitre.getMatchs());

        return arbitreRepository.save(existingArbitre);
    }

    public Arbitre assignRefereeToTeam(Long arbitreId, Long equipeId) {
        Arbitre arbitre = getArbitreById(arbitreId);
        Equipe equipe = equipeRepository.findById(equipeId)
                .orElseThrow(() -> new RuntimeException("Team not found with id=" + equipeId));

        // Remove from previous team if any
        if (arbitre.getEquipeLiee() != null) {
            Equipe oldTeam = arbitre.getEquipeLiee();
            if (oldTeam.getArbitres() != null) {
                oldTeam.getArbitres().remove(arbitre);
                equipeRepository.save(oldTeam);
            }
        }

        // Assign to new team
        arbitre.setEquipeLiee(equipe);
        if (equipe.getArbitres() != null && !equipe.getArbitres().contains(arbitre)) {
            equipe.getArbitres().add(arbitre);
            equipeRepository.save(equipe);
        }

        return arbitreRepository.save(arbitre);
    }


    public Arbitre removeRefereeFromTeam(Long arbitreId) {
        Arbitre arbitre = getArbitreById(arbitreId);
        
        if (arbitre.getEquipeLiee() != null) {
            Equipe team = arbitre.getEquipeLiee();
            if (team.getArbitres() != null) {
                team.getArbitres().remove(arbitre);
                equipeRepository.save(team);
            }
            arbitre.setEquipeLiee(null);
        }

        return arbitreRepository.save(arbitre);
    }

    public void deleteArbitre(Long id) {
        Arbitre arbitre = arbitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbitre non trouvé avec id " + id));
        
        // Remove referee from team's referee list before deletion
        if (arbitre.getEquipeLiee() != null) {
            Equipe team = arbitre.getEquipeLiee();
            if (team.getArbitres() != null) {
                team.getArbitres().remove(arbitre);
                equipeRepository.save(team);
            }
        }
        
        arbitreRepository.delete(arbitre);
    }

    public long getArbitresCount() {
        return arbitreRepository.countArbitres();
    }
}
