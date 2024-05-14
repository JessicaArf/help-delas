package com.elastech.helpdelas.controller;

import com.elastech.helpdelas.dtos.RequestPasswordDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.service.RequestPasswordService;
import com.elastech.helpdelas.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;

@Controller
public class RequestPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestPasswordService forgotPasswordService;


    @GetMapping("solicitar-nova-senha")
    public String passwordRequest() {
        return "/default/password-request";
    }

    @PostMapping("solicitar-nova-senha")
    public String savePasswordRequest(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) throws MessagingException, UnsupportedEncodingException {
        UserDTO userDto = userService.getUserByEmail(email);

        if (userDto == null) {
            model.addAttribute("error", "Esse email não está cadastrado.");
            return "/default/password-request";
        }

        if (userDto.getRole().getName().equals("ADMIN")) {
            model.addAttribute("error", "Não é possível solicitar uma nova senha para um usuário admin.");
            return "/default/password-request";
        }

        RequestPasswordDTO request = forgotPasswordService.saveToken(userDto);

        String emailLink = "http://localhost:8080/resetar-senha?token=" + request.getToken();

        try {
            forgotPasswordService.sendEmail(userDto.getEmail(), "Redefinição de Senha", emailLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Erro ao enviar email.");
            return "/default/password-request";
        }
        return "redirect:/solicitar-nova-senha?success";
    }

    @GetMapping("/resetar-senha")
    public String resetPassword(@Param(value = "token") String token, Model model, HttpSession session) {
        session.setAttribute("token", token);
        RequestPasswordDTO request = forgotPasswordService.getRequestByToken(token);
        if (request == null) {
            model.addAttribute("error", "Token inválido.");
            return "/default/error-page";
        }
        // Verificar se o token expirou
        else if (forgotPasswordService.isExpired(request)) {
            model.addAttribute("error", "O token está expirado.");
            return "/default/error-page";
        }  // Verificar se a solicitação já foi usada
        else if (request.isUsed()) {
            model.addAttribute("error", "O token já foi utilizado.");
            return "/default/error-page";
        } else {
            return "/default/reset-password";
        }
    }

    @PostMapping(("/resetar-senha"))
    public String saveResetPassword(HttpServletRequest request, HttpSession session, Model model) {
        String password = request.getParameter("password");
        String token = (String) session.getAttribute("token");
        RequestPasswordDTO requestPassword = forgotPasswordService.getRequestByToken(token);
        // Resetar a senha
        forgotPasswordService.resetPassword(requestPassword, password);

        session.setAttribute("senhaAlterada", true);
        return "redirect:/login?senhaAlterada=true";
    }

}
