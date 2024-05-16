package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.PriorityDTO;
import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.service.PriorityService;
import com.elastech.helpdelas.service.SectorService;
import com.elastech.helpdelas.service.TicketService;
import com.elastech.helpdelas.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class TicketsController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private SectorService sectorService;

    @GetMapping("/criar-chamado")
    public String showPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO basicUser = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> sectors = sectorService.findAllSector();
        model.addAttribute("user", basicUser);
        model.addAttribute("sectors", sectors.get(0));
        return "ticket/create-ticket";
    }

    @PostMapping("/criar-chamado")
    public String createTicket(TicketDTO ticketDTO, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) throws MessagingException, UnsupportedEncodingException {
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

        // Adiciona os tickets ao modelo
        model.addAttribute("tickets", tickets);
        return "user/dashboard-user";
    }

    @GetMapping("/usuario/editar-chamado/{ticketId}")
    public String showUserEditTicket(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userBasic = userService.getUserByEmail(userDetails.getUsername());

        model.addAttribute("sector", userBasic.getSector());
        model.addAttribute("user", userBasic);
        model.addAttribute("ticket", ticket);

        return "ticket/update-ticket-user";
    }

    @PutMapping("/usuario/editar-chamado/{ticketId}")
    public String userUpdateTicket(@PathVariable Long ticketId, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TicketDTO ticket) throws MessagingException, UnsupportedEncodingException {
        ticketService.updateTicketUser(ticketId, ticket);
        return "redirect:/dashboard-usuario";
    }

    @GetMapping("/usuario/chamado/{ticketId}")
    public String showOneTicketUser(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userBasic = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userBasic.getName());
        model.addAttribute("ticket", ticket);
        return "ticket/view-one-ticket-user";
    }

    @DeleteMapping("/usuario/chamado/{ticketId}")
    public String deleteTicket(@PathVariable Long ticketId) throws MessagingException, UnsupportedEncodingException {
        ticketService.deleteTicket(ticketId);
        return "redirect:/dashboard-usuario";
    }

    @GetMapping("/dashboard-tecnico")
    public String showTicketsAvailable(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO techUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", techUser.getName());
        List<TicketDTO> tickets = ticketService.showTicketsAvailable();
        model.addAttribute("ticketsAvailable", tickets);
        return "tech/dashboard-tech";
    }

    @GetMapping("/dashboard-tecnico/meus-chamados")
    public String showTicketsAssigned(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO techUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", techUser.getName());
        List<TicketDTO> tickets = ticketService.showTicketsAssigned(techUser.getUserId());
        model.addAttribute("ticketsAssigned", tickets);
        return "tech/dashboard-tech-assigned";
    }

    @GetMapping("/tecnico/chamado/{ticketId}")
    public String showOneTicketTech(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userTech = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userTech.getName());
        model.addAttribute("ticket", ticket);
        return "ticket/view-one-ticket-tech";
    }

    @GetMapping("/tecnico/editar-chamado/{ticketId}")
    public String showTechEditTicket(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userTech = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> sectors = sectorService.findAllSector();
        List<PriorityDTO> priorities = priorityService.findAllPriority();

        model.addAttribute("priorities", priorities);
        model.addAttribute("sectors", sectors);
        model.addAttribute("name", userTech.getName());
        model.addAttribute("ticket", ticket);

        return "ticket/update-ticket-tech";
    }

    @PutMapping("/tecnico/editar-chamado/{ticketId}")
    public String techUpdateTicket(@PathVariable Long ticketId, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TicketDTO ticket) throws MessagingException, UnsupportedEncodingException {
        UserDTO userTech = userService.getUserByEmail(userDetails.getUsername());
        ticketService.updateTicketTech(ticketId, ticket, userTech);
        return "redirect:/dashboard-tecnico/meus-chamados";
    }

    @GetMapping("/admin/todos-chamados")
    public String showAllTickets(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO adminUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", adminUser.getName());
        List<TicketDTO> allTickets = ticketService.showAllTickets();
        model.addAttribute("allTickets", allTickets);
        return "admin/all-tickets";
    }

    @GetMapping("/admin/chamados-tecnico")
    public String showAllTicketsTech(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO adminUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", adminUser.getName());
        List<TicketDTO> allTicketTech = ticketService.showAllTicketsTech();
        model.addAttribute("allTicketTech", allTicketTech);
        return "admin/all-tickets-tech";
    }

    @GetMapping("/admin/chamados-nao-atribuido")
    public String showAllTicketsNotAssigned(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO adminUser = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", adminUser.getName());
        List<TicketDTO> allTicketsNotAssigned = ticketService.showTicketsAvailable();
        model.addAttribute("allTicketsNotAssigned", allTicketsNotAssigned);
        return "admin/all-tickets-no-assigned";
    }

    @GetMapping("/admin/chamado/{ticketId}")
    public String showOneTicketAdmin(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TicketDTO ticket = ticketService.showTicketById(ticketId);
        UserDTO userTech = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userTech.getName());
        model.addAttribute("ticket", ticket);
        return "ticket/view-one-ticket-admin";
    }

}


