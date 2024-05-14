package com.elastech.helpdelas.repositories;

import com.elastech.helpdelas.model.RequestPasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestPasswordRepository extends JpaRepository<RequestPasswordModel, Long> {
    Optional<RequestPasswordModel> findByToken(String token);
}
