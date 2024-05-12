package com.elastech.helpdelas.repositories;

import com.elastech.helpdelas.model.RequestPasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestPasswordRepository extends JpaRepository<RequestPasswordModel, Long> {
    RequestPasswordModel findByToken(String token);
}
