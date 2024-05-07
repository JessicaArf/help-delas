package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.service.TechService;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TechController {

    @Autowired
    private TechService techService;

    @Autowired
    private UserService userService;

    @GetMapping("/salvar-tech")
    public String showPageRegister(Model model){
        List<SectorDTO> sectors = userService.findAllSector();
        model.addAttribute("sectors", sectors);
        return "tech/register-tech";
    }

    // Esse metodo chama a pagina de HTML
    @GetMapping("/dashboard-tecnico")
    public String showTech(){
        return "tech/dashboard-tech";
    }

    @PostMapping("/salvar-tech")
    public String register(UserDTO userDTO, RedirectAttributes redirectAttributes) throws Exception {
        try {
            techService.registerTech(userDTO);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/salvar-tech";
        }
    }
}
