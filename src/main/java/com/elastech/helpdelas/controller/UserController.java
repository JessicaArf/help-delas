package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/salvar-usuario")
    public String register(){
        return "user/register";
    }

    @GetMapping("user/register")
    public String registerData(){
        return "user/register";
    }

    @GetMapping("user/dashboard-user")
    public String dashboardUser(){
        return "/user/dashboard-user";
    }

    @PostMapping("/salvar-usuario")
    public String register(UserModel userModel){
        userService.salvar(userModel);
        return "redirect:/default/login";
    }
}
