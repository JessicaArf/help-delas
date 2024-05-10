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

    @GetMapping("/cadastrar-tecnico")
    public String showPageRegister(Model model){
        List<SectorDTO> sectors = userService.findAllSector();
        model.addAttribute("sectors", sectors);
        return "admin/register-tech";
    }

    @PostMapping("/cadastrar-tecnico")
    public String register(UserDTO userDTO, RedirectAttributes redirectAttributes) throws Exception {
        try {
            techService.registerTech(userDTO);
            return "redirect:/dashboard-admin";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/dashboard-admin";
        }
    }

}
