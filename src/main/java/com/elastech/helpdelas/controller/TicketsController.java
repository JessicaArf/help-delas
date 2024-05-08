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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketsController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/criar-chamado")
    public String showPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO basicUser = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> sectors = userService.findAllSector();
        model.addAttribute("name", basicUser.getName());
        model.addAttribute("sectors", sectors);
        return "ticket/create-ticket";
    }

    @PostMapping("/criar-chamado")
    public String createTicket(TicketDTO ticketDTO, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        // pegando o usuário logado através do userdetails e puxando o usuário do banco de dados pelo email
        UserDTO basicUser = userService.getUserByEmail(userDetails.getUsername());
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

    @GetMapping("/usuario/editar-chamado/{ticketId}")
    public String showUserEditTicket(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userBasic = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> sectors = userService.findAllSector();

        model.addAttribute("sectors", sectors);
        model.addAttribute("name", userBasic.getName());
        model.addAttribute("ticket", ticket);

        return "ticket/update-ticket-user";
    }

    @PutMapping("/usuario/editar-chamado/{ticketId}")
    public String userUpdateTicket(@PathVariable Long ticketId, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TicketDTO ticket) {
        ticketService.updateTicketUser(ticketId, ticket);
        return "redirect:/dashboard-usuario";
    }

    @GetMapping("/usuario/chamado/{ticketId}")
    public String showOneTicket(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userTech = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userTech.getName());
        model.addAttribute("ticket", ticket);

        return "ticket/view-one-ticket";
    }

    @DeleteMapping("/usuario/chamado/{ticketId}")
    public String deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return "redirect:/dashboard-usuario";
    }

    @GetMapping("/dashboard-tecnico")
    public String showTicketsAvailable(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO techUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", techUser.getName());
        List<TicketDTO> tickets = ticketService.showTicketsAvailable();
        if (tickets == null || tickets.isEmpty()) {

            tickets = new ArrayList<>();
        }
        model.addAttribute("ticketsAvailable", tickets);
        return "tech/dashboard-tech";
    }

    @GetMapping("/dashboard-tecnico/meus-chamados")
    public String showTicketsAssigned(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO techUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", techUser.getName());
        List<TicketDTO> tickets = ticketService.showTicketsAssigned(techUser.getUserId());

        if (tickets == null || tickets.isEmpty()) {

            tickets = new ArrayList<>();
        }
        model.addAttribute("ticketsAssigned", tickets);
        return "tech/dashboard-tech-assigned";
    }


    @GetMapping("/tecnico/editar-chamado/{ticketId}")
    public String showTechEditTicket(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userTech = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> sectors = userService.findAllSector();

        model.addAttribute("sectors", sectors);
        model.addAttribute("name", userTech.getName());
        model.addAttribute("ticket", ticket);

        return "ticket/update-ticket-tech";
    }

    @PutMapping("/tecnico/editar-chamado/{ticketId}")
    public String techUpdateTicket(@PathVariable Long ticketId, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TicketDTO ticket) {
        UserDTO userTech = userService.getUserByEmail(userDetails.getUsername());
        ticketService.updateTicketTech(ticketId, ticket, userTech);
        return "redirect:/dashboard-tecnico/meus-chamados";
    }

    @GetMapping("/admin/todos-chamados")
    public String showAllTickets(Model model, @AuthenticationPrincipal UserDetails userDetails){
        UserDTO adminUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", adminUser);

        List<TicketDTO> allTickets = ticketService.showAllTickets();
        model.addAttribute("allTickets", allTickets);

        List<TicketDTO> allTicketTech = ticketService.showAllTicketsTech();
        model.addAttribute("allTicketTech", allTicketTech);

        List<TicketDTO> allTicketsNotAssigned = ticketService.showTicketsAvailable();
        model.addAttribute("allTicketsNotAssigned", allTicketsNotAssigned);
        return "admin/table-tickets";
    }

}


