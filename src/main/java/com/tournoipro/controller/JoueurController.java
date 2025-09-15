package com.tournoipro.controller;

import com.tournoipro.dto.JoueurWithTeamDto;
import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
import com.tournoipro.service.JoueurService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/joueurs")
@AllArgsConstructor
@Tag(name = "Joueurs", description = "Player management APIs")

public class JoueurController{

    private final JoueurService joueurService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public JoueurWithTeamDto createJoueur(@RequestBody Joueur joueur) {
        Joueur createdJoueur = joueurService.createJoueur(joueur);
        // Convert to DTO
        return new JoueurWithTeamDto(
            createdJoueur.getId(),
            createdJoueur.getNom(),
            createdJoueur.getRole(),
            createdJoueur.getType(),
            createdJoueur.getEquipe() != null ? createdJoueur.getEquipe().getId() : null,
            createdJoueur.getEquipe() != null ? createdJoueur.getEquipe().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<JoueurWithTeamDto> getAllJoueurs() {
        return joueurService.getAllJoueursWithTeamInfo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/with-teams")
    public List<JoueurWithTeamDto> getAllJoueursWithTeamInfo() {
        return joueurService.getAllJoueursWithTeamInfo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public JoueurWithTeamDto getJoueurById(@PathVariable Long id) {
        Joueur joueur = joueurService.getJoueurById(id);
        // Convert to DTO
        return new JoueurWithTeamDto(
            joueur.getId(),
            joueur.getNom(),
            joueur.getRole(),
            joueur.getType(),
            joueur.getEquipe() != null ? joueur.getEquipe().getId() : null,
            joueur.getEquipe() != null ? joueur.getEquipe().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public JoueurWithTeamDto updateJoueur(@PathVariable Long id, @RequestBody Joueur joueur) {
        Joueur updatedJoueur = joueurService.updateJoueur(id, joueur);
        // Convert to DTO
        return new JoueurWithTeamDto(
            updatedJoueur.getId(),
            updatedJoueur.getNom(),
            updatedJoueur.getRole(),
            updatedJoueur.getType(),
            updatedJoueur.getEquipe() != null ? updatedJoueur.getEquipe().getId() : null,
            updatedJoueur.getEquipe() != null ? updatedJoueur.getEquipe().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteJoueur(@PathVariable Long id) {
        joueurService.deleteJoueur(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/joueurs")
    public long countJoueurs() {
        return joueurService.getJoueursCount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/type/{type}")
    public List<JoueurWithTeamDto> getJoueursByType(@PathVariable TypeJoueur type) {
        List<Joueur> joueurs = joueurService.getJoueurByType(type);
        return joueurs.stream().map(joueur -> 
            new JoueurWithTeamDto(
                joueur.getId(),
                joueur.getNom(),
                joueur.getRole(),
                joueur.getType(),
                joueur.getEquipe() != null ? joueur.getEquipe().getId() : null,
                joueur.getEquipe() != null ? joueur.getEquipe().getNom() : null
            )
        ).collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public List<JoueurWithTeamDto> searchJoueursByNom(@RequestParam String nom) {
        List<Joueur> joueurs = joueurService.searchJoueursByNom(nom);
        return joueurs.stream().map(joueur -> 
            new JoueurWithTeamDto(
                joueur.getId(),
                joueur.getNom(),
                joueur.getRole(),
                joueur.getType(),
                joueur.getEquipe() != null ? joueur.getEquipe().getId() : null,
                joueur.getEquipe() != null ? joueur.getEquipe().getNom() : null
            )
        ).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/equipes/{equipeId}/joueurs")
    public List<JoueurWithTeamDto> getJoueursByEquipeId(@PathVariable Long equipeId) {
        List<Joueur> joueurs = joueurService.getJoueursByEquipeId(equipeId);
        return joueurs.stream().map(joueur -> 
            new JoueurWithTeamDto(
                joueur.getId(),
                joueur.getNom(),
                joueur.getRole(),
                joueur.getType(),
                joueur.getEquipe() != null ? joueur.getEquipe().getId() : null,
                joueur.getEquipe() != null ? joueur.getEquipe().getNom() : null
            )
        ).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{playerId}/assign-team/{equipeId}")
    public JoueurWithTeamDto assignPlayerToTeam(@PathVariable Long playerId, @PathVariable Long equipeId) {
        Joueur joueur = joueurService.assignPlayerToTeam(playerId, equipeId);
        // Convert to DTO
        return new JoueurWithTeamDto(
            joueur.getId(),
            joueur.getNom(),
            joueur.getRole(),
            joueur.getType(),
            joueur.getEquipe() != null ? joueur.getEquipe().getId() : null,
            joueur.getEquipe() != null ? joueur.getEquipe().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{playerId}/unassign-team")
    public JoueurWithTeamDto unassignPlayerFromTeam(@PathVariable Long playerId) {
        Joueur joueur = joueurService.unassignPlayerFromTeam(playerId);
        // Convert to DTO
        return new JoueurWithTeamDto(
            joueur.getId(),
            joueur.getNom(),
            joueur.getRole(),
            joueur.getType(),
            joueur.getEquipe() != null ? joueur.getEquipe().getId() : null,
            joueur.getEquipe() != null ? joueur.getEquipe().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/unassigned")
    public List<JoueurWithTeamDto> getUnassignedPlayers() {
        List<Joueur> joueurs = joueurService.getUnassignedPlayers();
        return joueurs.stream().map(joueur -> 
            new JoueurWithTeamDto(
                joueur.getId(),
                joueur.getNom(),
                joueur.getRole(),
                joueur.getType()
            )
        ).collect(Collectors.toList());
    }
}
