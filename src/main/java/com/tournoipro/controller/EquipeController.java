package com.tournoipro.controller;

import com.tournoipro.model.Equipe;
import com.tournoipro.service.EquipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
@Tag(name = "Equipes", description = "Team management APIs")

public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Equipe createEquipe(@RequestBody Equipe equipe) {
        return equipeService.createEquipe(equipe);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Equipe> getAllEquipes() {
        return equipeService.getAllEquipes();
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
    public Equipe updateEquipe(@PathVariable Long id, @RequestBody Equipe equipe) {
        return equipeService.updateEquipe(id, equipe);
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
}
