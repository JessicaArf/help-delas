package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.service.TicketService;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TicketController {


    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/criar-chamado")
    public String showPage(Model model) {
        List<SectorDTO> sectors = userService.findAllSector();
        model.addAttribute("sectors", sectors);
        return "ticket/create-ticket";
    }

    @PostMapping("/criar-chamado")
    public String createTicket(TicketDTO ticketDTO, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        // pegando o usuário logado através do userdetails e puxando o usuário do banco de dados pelo email
        UserDTO basicUser = userService.getUserByEmail(userDetails.getUsername());
        System.out.println(basicUser);
        // passando o ticket e o usuário para que o ticket seja salvo
        ticketService.createTicket(ticketDTO, basicUser);
        // redirecionado para o dashboard do usuário
        return "redirect:/dashboard-usuario";
    }

    @GetMapping("/dashboard-usuario")
    public String showTicketsByUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // pegando o usuário logado através do userdetails e puxando o usuário do banco de dados pelo email
        UserDTO basicUser = userService.getUserByEmail(userDetails.getUsername());
        // adicionando o usuário como modelo
        model.addAttribute("basicUser", basicUser);
        // pegando a lista de tickets pelo id do usuário
        List<TicketDTO> tickets = ticketService.showTicketsByUser(basicUser.getUserId());

        // Verifica se a lista de tickets é nula ou vazia
        if (tickets == null || tickets.isEmpty()) {
            // Se não houver tickets, cria uma lista vazia
            tickets = new ArrayList<>();
        }

        // Adiciona os tickets ao modelo
        model.addAttribute("tickets", tickets);
        return "ticket/dashboard-user";
    }

}


