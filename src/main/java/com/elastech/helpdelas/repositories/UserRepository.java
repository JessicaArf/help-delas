package com.elastech.helpdelas.repositories;


import com.elastech.helpdelas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String username);
    List<UserModel> findBySectorSectorId(Long sectorId);
}
