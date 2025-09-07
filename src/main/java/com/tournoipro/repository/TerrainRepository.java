package com.tournoipro.repository;

import com.tournoipro.model.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TerrainRepository extends JpaRepository<Terrain, Long> {

}

