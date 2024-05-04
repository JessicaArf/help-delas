package com.elastech.helpdelas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("helpdelas")
public class ConfigSiteController {

    @GetMapping("/index")
    public String loadSite(){
        return "index";
    }

    @GetMapping("default/login")
    public String loadSystem(){
        return "default/login";
    }

    @GetMapping("user/register")
    public String register(){
        return "/user/register";
    }

    @GetMapping("user/dashboard-user")
    public String dashboardUser(){
        return "/user/dashboard-user";
    }

}
