package com.tournoipro.controller;

import com.tournoipro.dto.EquipeWithPlayersDto;
import com.tournoipro.model.Equipe;
import com.tournoipro.service.EquipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
@Tag(name = "Equipes", description = "Team management APIs")
@CrossOrigin(origins = "http://localhost:4200")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createEquipe(@RequestBody Equipe equipe) {
        try {
            Equipe savedEquipe = equipeService.createEquipe(equipe);
            return ResponseEntity.ok(savedEquipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating team: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Equipe> getAllEquipes() {
        return equipeService.getAllEquipes();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/with-basic-info")
    public List<EquipeWithPlayersDto> getAllEquipesWithBasicInfo() {
        return equipeService.getAllEquipesWithBasicInfo();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Equipe getEquipeById(@PathVariable Long id) {
        return equipeService.getEquipeById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/equipes/{nom}")
    public List<Equipe> getEquipesByNom(@PathVariable String nom) {
        return equipeService.getEquipesByName(nom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/poule/{pouleId}")
    public List<Equipe> getEquipesByPoule(@PathVariable Long pouleId) {
        return  equipeService.getEquipesByPoule(pouleId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sans-poule")
    public List<Equipe> getEquipesSansPoule() {
        return  equipeService.getEquipesSansPoule();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipe(@PathVariable Long id, @RequestBody Equipe equipe) {
        try {
            Equipe updatedEquipe = equipeService.updateEquipe(id, equipe);
            return ResponseEntity.ok(updatedEquipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating team: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteEquipe(@PathVariable Long id) {
        equipeService.deleteEquipe(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/equipes")
    public long countEquipes() {
        return equipeService.getEquipesCount();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{equipeId}/players/{playerId}")
    public ResponseEntity<?> addPlayerToTeam(@PathVariable Long equipeId, @PathVariable Long playerId) {
        try {
            Equipe updatedEquipe = equipeService.addPlayerToTeam(equipeId, playerId);
            return ResponseEntity.ok(updatedEquipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding player to team: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{equipeId}/players/{playerId}")
    public ResponseEntity<?> removePlayerFromTeam(@PathVariable Long equipeId, @PathVariable Long playerId) {
        try {
            Equipe updatedEquipe = equipeService.removePlayerFromTeam(equipeId, playerId);
            return ResponseEntity.ok(updatedEquipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing player from team: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{equipeId}/referees/{arbitreId}")
    public ResponseEntity<?> addRefereeToTeam(@PathVariable Long equipeId, @PathVariable Long arbitreId) {
        try {
            Equipe updatedEquipe = equipeService.addRefereeToTeam(equipeId, arbitreId);
            return ResponseEntity.ok(updatedEquipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding referee to team: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{equipeId}/referees/{arbitreId}")
    public ResponseEntity<?> removeRefereeFromTeam(@PathVariable Long equipeId, @PathVariable Long arbitreId) {
        try {
            Equipe updatedEquipe = equipeService.removeRefereeFromTeam(equipeId, arbitreId);
            return ResponseEntity.ok(updatedEquipe);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing referee from team: " + e.getMessage());
        }
    }
}