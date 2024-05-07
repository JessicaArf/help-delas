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
        UserDTO userBasic = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userBasic.getName());
        // adicionando o usuário como modelo
        model.addAttribute("basicUser", userBasic);
        // pegando a lista de tickets pelo id do usuário
        List<TicketDTO> tickets = ticketService.showTicketsByUser(userBasic.getUserId());

        // Verifica se a lista de tickets é nula ou vazia
        if (tickets == null || tickets.isEmpty()) {
            // Se não houver tickets, cria uma lista vazia
            tickets = new ArrayList<>();
        }

        // Adiciona os tickets ao modelo
        model.addAttribute("tickets", tickets);
        return "user/dashboard-user";
    }

    @GetMapping("/dashboard-tecnico")
    public String showTicketsAvailable(Model model, @AuthenticationPrincipal UserDetails userDetails){
    List<TicketDTO> tickets = ticketService.showTicketsAvailable();

        if (tickets == null || tickets.isEmpty()) {

            tickets = new ArrayList<>();
        }
        model.addAttribute("ticketsAvailable", tickets);
        return "tech/dashboard-tech";
    }

   @GetMapping("/dashboard-tecnico/meus-chamados")
    public String showTicketsAssigned(Model model, @AuthenticationPrincipal UserDetails userDetails){
        UserDTO techUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("techUser", techUser);
        List<TicketDTO> tickets = ticketService.showTicketsAssigned(techUser.getUserId());

        if (tickets == null || tickets.isEmpty()) {

            tickets = new ArrayList<>();
        }
        model.addAttribute("ticketsAssigned", tickets);
        return "tech/dashboard-tech-assigned";
    }
}


