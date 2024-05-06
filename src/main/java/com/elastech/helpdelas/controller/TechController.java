package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.service.TechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TechController {

    @Autowired
    private TechService techService;

    @GetMapping("/salvar-tech")
    public String showPageRegister(){
        return "tech/register-tech";
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
