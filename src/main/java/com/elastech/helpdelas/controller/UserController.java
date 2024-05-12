package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.TicketDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
    public String showRegister(Model model){
        List<SectorDTO> sectors = userService.findAllSector();
        sectors.remove(0);
        model.addAttribute("sectors", sectors);
        return "user/register";
    }

    @GetMapping("/todos-usuarios")
    public String allUsers(Model model, @AuthenticationPrincipal UserDetails userDetails){
        List<UserDTO> users = userService.findAll();
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        users.remove(0); //remove o index do admin
        model.addAttribute("users", users);
        model.addAttribute("name", userDb.getName());
        return "admin/showAll-user";
    }

    @PostMapping("/salvar-usuario")
    public String register(UserDTO userDTO, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        try{
            userService.save(userDTO, userDetails);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/user/register";
        }
    }
    
    @GetMapping("/mostrar-usuario")
    public String showUser(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
            if (userDb != null) {
                model.addAttribute("user", userDb);
            }
            return "user/show-user";
        } catch (Exception e) {
            System.out.println(e);
            return "user/dashboard-user";
        }
    }

    @GetMapping("/editar-usuario")
    public String showEditUser(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
            List<SectorDTO> sectors = userService.findAllSector();
            model.addAttribute("sectors", sectors);
            model.addAttribute("user", userDb);
            return "user/edit-user";
        } catch (Exception e) {
            System.out.println(e);
            return "user/edit-user";
        }
    }

    @PutMapping("/editar-usuario")
    public String editUser(Model model, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes, @ModelAttribute UserDTO userModel) throws Exception {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        Long userId = userDb.getUserId();
        try {
            if (userDb != null) {
                UserDTO userAtualizado = userService.updateUserById(userId, userModel);
                model.addAttribute("user", userAtualizado);
            }
            return "user/show-user";

        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            model.addAttribute("user", userDb);
            return "user/edit-user";
        }
    }

    @GetMapping("/mostrar-usuario/{id}")
    public String showUserId(@PathVariable Long id, Model model){
        try {
            UserDTO userDb = userService.getUserById(id);
            model.addAttribute("user", userDb);
            return "admin/show-user-admin";
        } catch (Exception e) {
            System.out.println(e);
            return "admin/showAll-user";
        }
    }

    @GetMapping("/desativar-usuario/{id}")
    public String disableById(@PathVariable Long id, Model model){
        try {
            userService.updateStatus(id, "desativar");
            List<UserDTO> users = userService.findAll();
            users.remove(0); //remove o index do admin
            model.addAttribute("users", users);
            return "admin/showAll-user";
        } catch (Exception e) {
            System.out.println(e);
            return "admin/showAll-user";
        }
    }

    @GetMapping("/ativar-usuario/{id}")
    public String activateById(@PathVariable Long id, Model model){
        try {
            userService.updateStatus(id, "ativar");
            List<UserDTO> users = userService.findAll();
            users.remove(0); //remove o index do admin
            model.addAttribute("users", users);
            return "admin/showAll-user";
        } catch (Exception e) {
            System.out.println(e);
            return "admin/showAll-user";
        }
    }

    @GetMapping("/mostrar-tecnico")
    public String showTech(Model model, @AuthenticationPrincipal UserDetails userDetails){
        try {
            UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
            if (userDb != null) {
                model.addAttribute("user", userDb);
            }
            return "tech/show-tech";
        } catch (Exception e) {
            System.out.println(e);
            return "tech/dashboard-tech";
        }
    }

    @GetMapping("/cadastrar-tecnico")
    public String showPageRegister(Model model, @AuthenticationPrincipal UserDetails userDetails){
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> sectors = userService.findAllSector();
        model.addAttribute("name", userDb.getName());
        model.addAttribute("sectors", sectors.get(0));
        return "admin/register-tech";
    }

    @PostMapping("/cadastrar-tecnico")
    public String registerTech(UserDTO userDTO, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        try {
            userService.save(userDTO, userDetails);
            return "redirect:/dashboard-admin";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/dashboard-admin";
        }
    }

}
