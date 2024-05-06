package com.elastech.helpdelas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketController {

    @GetMapping("ticket/create-ticket")
    public String createTicket(){
        return "ticket/create-ticket";
    }
}
