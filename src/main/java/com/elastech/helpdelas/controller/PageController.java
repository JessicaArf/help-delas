package com.elastech.helpdelas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @GetMapping("/index")
    public String loadSite(){
        return "index";
    }

    @GetMapping("default/login")
    public String loadSystem(){
        return "default/login";
    }

}
