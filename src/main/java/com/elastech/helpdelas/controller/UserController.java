package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String register(UserDTO userDTO, RedirectAttributes redirectAttributes) throws Exception {
        try{
            userService.salvar(userDTO);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/user/register";
        }
    }

    @GetMapping("user/dashboard-user")
    public String showUserDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        try {
            UserModel userDb = userService.find(userDetails.getUsername());
            if (userDb != null) {
                model.addAttribute("user", userDb);
            }
            return "user/dashboard-user";
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/login";
        }
    }

    @GetMapping("user/show-user")
    public String showUser(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            UserModel userDb = userService.find(userDetails.getUsername());
            if (userDb != null) {
                model.addAttribute("user", userDb);
            }
            return "/user/show-user";
        } catch (Exception e) {
            System.out.println(e);
            return "user/dashboard-user";
        }
    }

    @GetMapping("user/edit-user")
    public String editUser(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            UserModel userDb = userService.find(userDetails.getUsername());
            List<SectorDTO> sectors = userService.findAllSector();
            model.addAttribute("sectors", sectors);
            model.addAttribute("user", userDb);
            return "user/edit-user";
        } catch (Exception e) {
            System.out.println(e);
            return "user/dashboard-user";
        }
    }

    @PostMapping("/editar-usuario")
    public String editUser(Model model, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes, @ModelAttribute UserDTO userModel) throws Exception {

        UserModel userDb = userService.find(userDetails.getUsername());
        try {
            if (userDb != null) {
                UserModel userAtualizado = userService.updateUserById(userDb, userModel);
                model.addAttribute("user", userAtualizado);
            }
            return "user/show-user";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            model.addAttribute("user", userDb);
            return "user/edit-user";
        }
    }

}
