package com.tournoipro.repository;

import com.tournoipro.model.Poule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PouleRepository extends JpaRepository<Poule, Long> {

}

