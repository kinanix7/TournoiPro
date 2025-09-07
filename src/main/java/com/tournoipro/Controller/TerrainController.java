package com.tournoipro.Controller;

import com.tournoipro.model.Terrain;
import com.tournoipro.service.TerrainService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrains")
@AllArgsConstructor
@Tag(name = "Terrains", description = "Court management APIs")

public class TerrainController {

    private final TerrainService terrainService;



    @PostMapping
    public Terrain createTerrain(@RequestBody Terrain terrain) {
        return terrainService.createTerrain(terrain);
    }

    @GetMapping
    public List<Terrain> getAllTerrains() {
        return terrainService.getAllTerrains();
    }

    @GetMapping("/{id}")
    public Terrain getTerrainById(@PathVariable Long id) {
        return terrainService.getTerrainById(id);
    }

    @PutMapping("/{id}")
    public Terrain updateTerrain(@PathVariable Long id, @RequestBody Terrain terrain) {
        return terrainService.updateTerrain(id, terrain);
    }

    @DeleteMapping("/{id}")
    public void deleteTerrain(@PathVariable Long id) {
        terrainService.deleteTerrain(id);
    }
}
