package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.ApiTicketDTO;
import com.elastech.helpdelas.service.ApiService;
import com.elastech.helpdelas.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("consultar-chamados")
public class ApiController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private ApiService apiService;

    @GetMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reports(Model model){


    }
}
