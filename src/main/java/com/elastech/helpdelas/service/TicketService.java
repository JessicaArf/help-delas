package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.model.TicketModel;
import com.elastech.helpdelas.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public TicketDTO createTicket(TicketDTO ticketDTO){
        TicketModel ticketModel = TicketDTO.convert(ticketDTO);
        ticketRepository.save(ticketModel);
        return ticketDTO;
    }


}
