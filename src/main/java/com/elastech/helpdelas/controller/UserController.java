package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.model.Sector;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/salvar-usuario")
    public String register(){
        return "user/register";
    }

    @GetMapping("user/register")
    public String registerData(Model model){
        List<SectorDTO> sectors = userService.findAllSector();
        model.addAttribute("sectors", sectors);
        return "user/register";
    }

    @PostMapping("/salvar-usuario")
    public String register(UserModel userModel, RedirectAttributes redirectAttributes) throws Exception {
        try{
            userService.salvar(userModel);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/user/register";
        }
    }
    @GetMapping("user/dashboard-user")
    public String showClient(Model model){
        UserModel user = getUser();
        if(user != null){
            UserModel userObject = userService.userFindById(user.getUserId());
            model.addAttribute("user", userObject);
        }
        return "redirect:/user/dashboard-user";
    }

    private UserModel getUser(){
        String user = null;
        Model model = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            user = authentication.getName();
        }
        UserModel userObject = userService.find(user);
        model.addAttribute("user", user);
        return userObject;

    }
}
