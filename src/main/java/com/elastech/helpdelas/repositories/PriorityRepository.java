package com.elastech.helpdelas.repositories;

import com.elastech.helpdelas.model.PriorityModel;
import com.elastech.helpdelas.model.SectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriorityRepository  extends JpaRepository<PriorityModel, Long> {
    Optional<PriorityModel> findByNamePriority(String namePriority);
}
