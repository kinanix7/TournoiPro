package com.tournoipro.service;

import com.tournoipro.model.Terrain;
import com.tournoipro.repository.TerrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerrainService {

    private final TerrainRepository terrainRepository;

    public TerrainService(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    public Terrain createTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    public List<Terrain> getAllTerrains() {
        return terrainRepository.findAll();
    }

    public Terrain getTerrainById(Long id) {
        return terrainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Terrain non trouvé avec id=" + id));
    }


    public Terrain updateTerrain(Long id, Terrain terrainDetails) {
        Terrain terrain = getTerrainById(id);
        terrain.setNom(terrainDetails.getNom());
        terrain.setLocalisation(terrainDetails.getLocalisation());
        terrain.setMatchs(terrainDetails.getMatchs());
        return terrainRepository.save(terrain);
    }

    public void deleteTerrain(Long id) {
        Terrain terrain = getTerrainById(id);
        terrainRepository.delete(terrain);
    }
}
