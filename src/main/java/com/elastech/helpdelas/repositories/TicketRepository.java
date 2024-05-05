package com.elastech.helpdelas.repositories;

import com.elastech.helpdelas.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketModel, Long> {
}
