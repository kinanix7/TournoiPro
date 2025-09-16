package com.tournoipro.service;

import com.tournoipro.model.Terrain;
import com.tournoipro.repository.TerrainRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TerrainServiceTest {

    @Test
    void testCreateTerrain() {
        TerrainRepository mockRepository = mock(TerrainRepository.class);
        TerrainService terrainService = new TerrainService(mockRepository);

        Terrain terrain = new Terrain();
        terrain.setNom("Terrain Central");
        terrain.setLocalisation("Rabat");

        when(mockRepository.save(any(Terrain.class))).thenReturn(terrain);

        Terrain result = terrainService.createTerrain(terrain);

        assertNotNull(result);
        assertEquals("Terrain Central", result.getNom());
        assertEquals("Rabat", result.getLocalisation());

        verify(mockRepository, times(1)).save(terrain);
    }
}
