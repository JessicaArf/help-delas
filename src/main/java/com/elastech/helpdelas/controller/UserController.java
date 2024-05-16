package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.UserDTO;
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
    public String showRegister(Model model) {
        List<SectorDTO> sectors = userService.findAllSector();
        sectors.remove(0);
        model.addAttribute("sectors", sectors);
        return "user/register";
    }

    @GetMapping("/todos-usuarios")
    public String allUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<UserDTO> users = userService.findAll();
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        users.remove(0); //remove o index do admin
        model.addAttribute("users", users);
        model.addAttribute("name", userDb.getName());
        return "admin/showAll-user";
    }

    @PostMapping("/salvar-usuario")
    public String register(UserDTO userDTO, RedirectAttributes redirectAttributes) throws Exception {
        try {
            userService.save(userDTO);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/salvar-usuario";
        }
    }

    @GetMapping("/mostrar-usuario")
    public String showUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
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
    public String showEditUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
            UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
            List<SectorDTO> sectors = userService.findAllSector();
            model.addAttribute("sectors", sectors);
            model.addAttribute("user", userDb);
            return "user/edit-user";
    }

    @GetMapping("/editar-tecnico/{id}")
    public String showEditTech(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            UserDTO userDb = userService.getUserById(id);
            List<SectorDTO> sectors = userService.findAllSector();
            UserDTO login = userService.getUserByEmail(userDetails.getUsername());
            model.addAttribute("sectors", sectors);
            model.addAttribute("user", userDb);
            model.addAttribute("name", login.getName());
            return "admin/edit-tech-admin";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "admin/edit-tech-admin";
        }
    }

    @PutMapping("/editar-usuario")
    public String editUser(Model model, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes, @ModelAttribute UserDTO userModel) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        Long userId = userDb.getUserId();
        try {
            if (userDb != null) {
                UserDTO userAtualizado = userService.updateUserById(userId, userModel);
                model.addAttribute("user", userAtualizado);
            }
            return "user/show-user";

        } catch (Exception e) {
            model.addAttribute("user", userDb);
            return "redirect:/editar-usuario";
        }
    }

    @PutMapping("/editar-tecnico/{id}")
    public String editTech(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, Model model, RedirectAttributes redirectAttributes, @ModelAttribute UserDTO userModel) {
        UserDTO login = userService.getUserByEmail(userDetails.getUsername());
        UserDTO userDb = userService.getUserById(id);
        try {
            if (userDb != null) {
                UserDTO userAtualizado = userService.updateUserById(id, userModel);
                redirectAttributes.addAttribute("successEdit", true);
                model.addAttribute("user", userAtualizado);
                model.addAttribute("name", login.getName());
                return "redirect:/mostrar-usuario/{id}";
            }
            return "redirect:/editar-tecnico/{id}";

        } catch (Exception e) {
            model.addAttribute("user", userDb);
            return "redirect:/editar-tecnico/{id}";
        }
    }

    @GetMapping("/mostrar-usuario/{id}")
    public String showUserId(@PathVariable Long id, Model model) {
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
    public String disableById(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        try {
            userService.updateStatus(id, "desativar");
            List<UserDTO> users = userService.findAll();
            users.remove(0); //remove o index do admin
            model.addAttribute("users", users);
            model.addAttribute("name", userDb.getName());
            model.addAttribute("success", "Usuário desativado com sucesso!");
            return "admin/showAll-user";
        } catch (Exception e) {
            System.out.println(e);
            return "admin/showAll-user";
        }
    }

    @GetMapping("/ativar-usuario/{id}")
    public String activateById(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        try {
            userService.updateStatus(id, "ativar");
            model.addAttribute("name", userDb.getName());
            List<UserDTO> users = userService.findAll();
            users.remove(0); //remove o index do admin
            model.addAttribute("users", users);
            model.addAttribute("success", "Usuário ativado com sucesso!");
            return "admin/showAll-user";
        } catch (Exception e) {
            System.out.println(e);
            return "admin/showAll-user";
        }
    }

    @GetMapping("/mostrar-tecnico")
    public String showTech(Model model, @AuthenticationPrincipal UserDetails userDetails) {
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
    public String showPageRegister(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> sectors = userService.findAllSector();
        model.addAttribute("name", userDb.getName());
        model.addAttribute("sectors", sectors.get(0));
        return "admin/register-tech";
    }

    @PostMapping("/cadastrar-tecnico")
    public String registerTech(UserDTO userDTO, RedirectAttributes redirectAttributes) throws Exception {
        try {
            userService.saveTech(userDTO);
            redirectAttributes.addAttribute("success", true);
            return "redirect:/cadastrar-tecnico";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/cadastrar-tecnico";
        }
    }
}
