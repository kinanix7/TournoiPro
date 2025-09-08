package com.tournoipro.controller;

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
    public Arbitre createArbitre(@RequestBody Arbitre arbitre) {
        return arbitreService.createArbitre(arbitre);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Arbitre> getAllArbitres() {
        return arbitreService.getAllArbitres();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Arbitre getArbitreById(@PathVariable Long id) {
        return arbitreService.getArbitreById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Arbitre updateArbitre(@PathVariable Long id, @RequestBody Arbitre arbitre) {
        return arbitreService.updateArbitre(id, arbitre);
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
