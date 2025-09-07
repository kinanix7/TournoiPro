package com.tournoipro.repository;

import com.tournoipro.model.Classement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassementRepository extends JpaRepository<Classement, Long> {

}

