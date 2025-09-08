package com.tournoipro.controller;

import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
import com.tournoipro.service.JoueurService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joueurs")
@AllArgsConstructor
@Tag(name = "Joueurs", description = "Player management APIs")

public class JoueurController{

    private final JoueurService joueurService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Joueur createJoueur(@RequestBody Joueur joueur) {
        return joueurService.createJoueur(joueur);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Joueur> getAllJoueurs() {
        return joueurService.getAllJoueurs();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Joueur getJoueurById(@PathVariable Long id) {
        return joueurService.getJoueurById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Joueur updateJoueur(@PathVariable Long id, @RequestBody Joueur joueur) {
        return joueurService.updateJoueur(id, joueur);
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
    public List<Joueur> getJoueursByType(@PathVariable TypeJoueur type) {
        return joueurService.getJoueurByType(type);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public List<Joueur> searchJoueursByNom(@RequestParam String nom) {
        return  joueurService.searchJoueursByNom(nom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/equipes/{equipeId}/joueurs")
    public List<Joueur> getJoueursByEquipeId(@PathVariable Long equipeId) {
        return joueurService.getJoueursByEquipeId(equipeId);
    }
}
