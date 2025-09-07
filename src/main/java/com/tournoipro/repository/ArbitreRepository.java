package com.tournoipro.repository;

import com.tournoipro.model.Arbitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArbitreRepository extends JpaRepository<Arbitre, Long> {

}

