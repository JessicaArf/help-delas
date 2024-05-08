package com.elastech.helpdelas.repositories;

import com.elastech.helpdelas.model.SectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<SectorModel, Long> {

    static Object updateById() {
        return null;
    }
}
