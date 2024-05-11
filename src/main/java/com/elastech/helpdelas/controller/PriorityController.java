package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.PriorityDTO;
import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.service.PriorityService;
import com.elastech.helpdelas.service.TicketService;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PriorityController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;
    @Autowired
    private PriorityService priorityService;

    @GetMapping("/registrar-prioridade")
    public String save(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userDb.getName());
        return "priority/register-priority";
    }

    @PostMapping("/registrar-prioridade")
    public String save(PriorityDTO dto, RedirectAttributes redirectAttributes) throws Exception {
        try{
            priorityService.save(dto);
            return "redirect:/dashboard-admin";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/registrar-prioridade";
        }
    }

    @GetMapping("/listar-prioridade")
    public String findAllPriority(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        List<PriorityDTO> priorities = priorityService.findAllPriority();
        List<TicketDTO> tickets = ticketService.showAllTickets();
        int size = tickets.size();

        model.addAttribute("name", userDb.getName());
        model.addAttribute("priorities", priorities);
        model.addAttribute("size", size);
        return "priority/find-priority";
    }

    @GetMapping("/visualizar-prioriade/{id}")
    public PriorityDTO findById(@PathVariable Long id) {
        return priorityService.findById(id);
    }

    @GetMapping("/editar-prioridade/{id}")
    public String showEditPriority(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userAdmin = userService.getUserByEmail(userDetails.getUsername());
        PriorityDTO priorityDTO = priorityService.findById(id);

        model.addAttribute("name", userAdmin.getEmail());
        model.addAttribute("priority", priorityDTO);
        return "priority/update-priority";
    }

    @PutMapping("/editar-prioridade/{id}")
    public String updatePriority(@PathVariable Long id, @ModelAttribute PriorityDTO priority){
        priorityService.updateById(priority, id);
        return "redirect:/listar-prioridade";
    }

    @DeleteMapping("/editar-prioriade/{priorityId}")
    public String deletePriority(@PathVariable Long  priorityId) {
        priorityService.deleteById(priorityId);
        return "redirect:/listar-prioridade";
    }
}
