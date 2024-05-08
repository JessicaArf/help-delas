package com.elastech.helpdelas.repositories;

import com.elastech.helpdelas.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    static Object updateById() {
        return null;
    }
}
