package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/criar-chamado")
     public String createTicket(TicketDTO ticketDTO) {
        ticketService.createTicket(ticketDTO);
        return "redirect:/user/dashboard-user";
    }

}
