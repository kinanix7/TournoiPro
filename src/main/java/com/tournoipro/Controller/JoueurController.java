package com.tournoipro.Controller;

import com.tournoipro.model.Joueur;
import com.tournoipro.service.JoueurService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joueurs")
@AllArgsConstructor
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
}
