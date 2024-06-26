package com.elastech.helpdelas.repositories;

import com.elastech.helpdelas.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketModel, Long> {
    List<TicketModel> findByUserBasicUserId(Long userBasicId);
    List<TicketModel> findByUserTechUserIdIsNull();
    List<TicketModel> findByUserTechUserId(Long technicianId);
    List<TicketModel> findAllByUserTechUserIdIsNotNull();
    List<TicketModel> findByPriorityPriorityId(Long priorityId);
    List<TicketModel> findByStatus(TicketModel.TicketStatus status);
}
