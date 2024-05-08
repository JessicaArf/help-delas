package com.elastech.helpdelas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String loadSite(){
        return "index";
    }

    @GetMapping("/login")
    public String loadSystem(){
        return "default/login";
    }

    @GetMapping("/dashboard-admin")
    public String showPage(){
        return "admin/dashboard-admin";
    }

    @GetMapping("/chamados-admin")
    public String tiketsAdmin() {
        return "admin/tickets-admin";
    }
}
