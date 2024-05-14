package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.PriorityDTO;
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
        try {
            priorityService.save(dto);
            redirectAttributes.addAttribute("success", true);
            return "redirect:/listar-prioridade";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/registrar-prioridade";
        }
    }

    @GetMapping("/listar-prioridade")
    public String findAllPriority(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        List<PriorityDTO> priorities = priorityService.findAllPriority();

        model.addAttribute("name", userDb.getName());
        model.addAttribute("priorities", priorities);
        return "priority/find-priority";
    }

    @GetMapping("/editar-prioridade/{priorityId}")
    public String showEditPriority(@PathVariable Long priorityId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userAdmin = userService.getUserByEmail(userDetails.getUsername());
        PriorityDTO priorityDTO = priorityService.findById(priorityId);

        model.addAttribute("name", userAdmin.getEmail());
        model.addAttribute("priority", priorityDTO);
        return "priority/update-priority";
    }

    @PutMapping("/editar-prioridade/{priorityId}")
    public String updatePriority(@PathVariable Long priorityId, @ModelAttribute PriorityDTO priority, RedirectAttributes redirectAttributes) throws Exception {
        try {
            PriorityDTO priorityDTO = priorityService.updateById(priority, priorityId);
            redirectAttributes.addAttribute("successEdit", true);
            return "redirect:/listar-prioridade";
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorEdit", true);
        }
        return "redirect:/editar-prioridade/{priorityId}";
    }

    @DeleteMapping("/editar-prioridade/{priorityId}")
    public String deletePriority(@PathVariable Long priorityId, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<TicketDTO> tickets = ticketService.showAllTicketsWithPriority(priorityId);
            if (tickets.isEmpty()) {
                priorityService.deleteById(priorityId);
                redirectAttributes.addAttribute("successDelete", true);
            } else {
                redirectAttributes.addAttribute("errorDelete", true);
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorDelete", true);
        }
        return "redirect:/listar-prioridade";
    }
}
