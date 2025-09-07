package com.tournoipro.Controller;

import com.tournoipro.model.Joueur;
import com.tournoipro.model.TypeJoueur;
import com.tournoipro.service.JoueurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joueurs")
@AllArgsConstructor
@Tag(name = "Joueurs", description = "Player management APIs")

public class JoueurController{

    private final JoueurService joueurService;


    @PostMapping
    public Joueur createJoueur(@RequestBody Joueur joueur) {
        return joueurService.createJoueur(joueur);
    }

    @GetMapping
    public List<Joueur> getAllJoueurs() {
        return joueurService.getAllJoueurs();
    }

    @GetMapping("/{id}")
    public Joueur getJoueurById(@PathVariable Long id) {
        return joueurService.getJoueurById(id);
    }

    @PutMapping("/{id}")
    public Joueur updateJoueur(@PathVariable Long id, @RequestBody Joueur joueur) {
        return joueurService.updateJoueur(id, joueur);
    }

    @DeleteMapping("/{id}")
    public void deleteJoueur(@PathVariable Long id) {
        joueurService.deleteJoueur(id);
    }

    @GetMapping("/count/joueurs")
    public long countJoueurs() {
        return joueurService.getJoueursCount();
    }

    @GetMapping("/type/{type}")
    public List<Joueur> getJoueursByType(@PathVariable TypeJoueur type) {
        return joueurService.getJoueurByType(type);

    }

    @GetMapping("/search")
    public List<Joueur> searchJoueursByNom(@RequestParam String nom) {
        return  joueurService.searchJoueursByNom(nom);
    }

    @GetMapping("/equipes/{equipeId}/joueurs")
    public List<Joueur> getJoueursByEquipeId(@PathVariable Long equipeId) {
        return joueurService.getJoueursByEquipeId(equipeId);
    }
}
