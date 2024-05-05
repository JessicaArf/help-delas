package com.elastech.helpdelas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/register")
    public String register(){
        return "/user/register";
    }

    @GetMapping("/dashboard-user")
    public String dashboardUser(){
        return "/user/dashboard-user";
    }

}
