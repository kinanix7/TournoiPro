package com.tournoipro.controller;

import com.tournoipro.dto.ArbitreWithTeamDto;
import com.tournoipro.model.Arbitre;
import com.tournoipro.service.ArbitreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arbitres")
@Tag(name = "Arbitres", description = "Referee management APIs")


public class ArbitreController {

    private final ArbitreService arbitreService;

    public ArbitreController(ArbitreService arbitreService) {
        this.arbitreService = arbitreService;
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ArbitreWithTeamDto createArbitre(@RequestBody Arbitre arbitre) {
        Arbitre createdArbitre = arbitreService.createArbitre(arbitre);
        // Convert to DTO
        return new ArbitreWithTeamDto(
            createdArbitre.getId(),
            createdArbitre.getNom(),
            createdArbitre.getEquipeLiee() != null ? createdArbitre.getEquipeLiee().getId() : null,
            createdArbitre.getEquipeLiee() != null ? createdArbitre.getEquipeLiee().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ArbitreWithTeamDto> getAllArbitres() {
        return arbitreService.getAllArbitresWithTeamInfo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/with-teams")
    public List<ArbitreWithTeamDto> getAllArbitresWithTeamInfo() {
        return arbitreService.getAllArbitresWithTeamInfo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ArbitreWithTeamDto getArbitreById(@PathVariable Long id) {
        Arbitre arbitre = arbitreService.getArbitreById(id);
        // Convert to DTO
        return new ArbitreWithTeamDto(
            arbitre.getId(),
            arbitre.getNom(),
            arbitre.getEquipeLiee() != null ? arbitre.getEquipeLiee().getId() : null,
            arbitre.getEquipeLiee() != null ? arbitre.getEquipeLiee().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ArbitreWithTeamDto updateArbitre(@PathVariable Long id, @RequestBody Arbitre arbitre) {
        Arbitre updatedArbitre = arbitreService.updateArbitre(id, arbitre);
        // Convert to DTO
        return new ArbitreWithTeamDto(
            updatedArbitre.getId(),
            updatedArbitre.getNom(),
            updatedArbitre.getEquipeLiee() != null ? updatedArbitre.getEquipeLiee().getId() : null,
            updatedArbitre.getEquipeLiee() != null ? updatedArbitre.getEquipeLiee().getNom() : null
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{arbitreId}/assign-team/{equipeId}")
    public ArbitreWithTeamDto assignRefereeToTeam(@PathVariable Long arbitreId, @PathVariable Long equipeId) {
        Arbitre arbitre = arbitreService.assignRefereeToTeam(arbitreId, equipeId);
        // Convert to DTO
        return new ArbitreWithTeamDto(
            arbitre.getId(),
            arbitre.getNom(),
            arbitre.getEquipeLiee() != null ? arbitre.getEquipeLiee().getId() : null,
            arbitre.getEquipeLiee() != null ? arbitre.getEquipeLiee().getNom() : null
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{arbitreId}/remove-team")
    public ArbitreWithTeamDto removeRefereeFromTeam(@PathVariable Long arbitreId) {
        Arbitre arbitre = arbitreService.removeRefereeFromTeam(arbitreId);
        // Convert to DTO
        return new ArbitreWithTeamDto(
            arbitre.getId(),
            arbitre.getNom(),
            arbitre.getEquipeLiee() != null ? arbitre.getEquipeLiee().getId() : null,
            arbitre.getEquipeLiee() != null ? arbitre.getEquipeLiee().getNom() : null
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteArbitre(@PathVariable Long id) {
        arbitreService.deleteArbitre(id);
        return "Arbitre supprimé avec succès (id=" + id + ")";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/arbitres")
    public long countArbitres() {
        return arbitreService.getArbitresCount();
    }
}
