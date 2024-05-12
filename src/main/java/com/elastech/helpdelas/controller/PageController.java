package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loadSite(){
        return "index";
    }

    @GetMapping("/login")
    public String loadSystem(){
        return "default/login";
    }

    @GetMapping("/dashboard-admin")
    public String showPage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
            if (userDb != null) {
                model.addAttribute("name", userDb.getName());
            }
            return "admin/dashboard-admin";
        } catch (Exception e) {
            System.out.println(e);
            return "admin/dashboard-admin";
        }
    }

    @GetMapping("/chamados-admin")
    public String tiketsAdmin() {
        return "admin/tickets-admin";
    }
}
