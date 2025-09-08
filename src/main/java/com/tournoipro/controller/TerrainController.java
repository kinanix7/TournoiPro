package com.tournoipro.controller;

import com.tournoipro.model.Terrain;
import com.tournoipro.service.TerrainService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrains")
@AllArgsConstructor
@Tag(name = "Terrains", description = "Court management APIs")

public class TerrainController {

    private final TerrainService terrainService;



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Terrain createTerrain(@RequestBody Terrain terrain) {
        return terrainService.createTerrain(terrain);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Terrain> getAllTerrains() {
        return terrainService.getAllTerrains();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Terrain getTerrainById(@PathVariable Long id) {
        return terrainService.getTerrainById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Terrain updateTerrain(@PathVariable Long id, @RequestBody Terrain terrain) {
        return terrainService.updateTerrain(id, terrain);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTerrain(@PathVariable Long id) {
        terrainService.deleteTerrain(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count/terrains")
    public long countTerrains() {
        return terrainService.getTerrainsCount();
    }
}
