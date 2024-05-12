package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.RequestPasswordDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.RequestPasswordModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.RequestPasswordRepository;
import com.elastech.helpdelas.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RequestPasswordService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RequestPasswordRepository forgotPasswordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public RequestPasswordDTO saveToken(UserDTO userDto) {
        UserModel userModel = UserDTO.convert(userDto);
        RequestPasswordModel forgotPassword = new RequestPasswordModel();
        forgotPassword.setExpireTime(expireTimeRange());
        forgotPassword.setToken(generateToken());
        forgotPassword.setUser(userModel);
        forgotPassword.setUsed(false);
        forgotPasswordRepository.save(forgotPassword);
        return new RequestPasswordDTO(forgotPassword);
    }

    public RequestPasswordDTO getRequestByToken(String token) {
        RequestPasswordModel request = forgotPasswordRepository.findByToken(token);
        return new RequestPasswordDTO(request);
    }

    public void resetPassword(RequestPasswordDTO request, String password) {
        UserModel user = request.getUser();
        user.setPassword(passwordEncoder.encode(password));
        request.setUsed(true);
        userRepository.save(user);
        RequestPasswordModel requestModel = RequestPasswordDTO.convert(request);
        forgotPasswordRepository.save(requestModel);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime expireTimeRange() {
        return LocalDateTime.now().plusMinutes(15);
    }

    public void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<h1>Olá!</h1>"
                + "<p>Clique no link para resetar sua senha:</p>"
                + "<p><a href=\"" + emailLink + "\">Resetar minha senha</a></p>"
                + "<br>"
                + "Ignore esse email se você não fez essa solicitação.";
        helper.setText(emailContent, true);
        helper.setFrom("helpdelas@outlook.com", "HelpDelas Suporte");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);
    }

    public boolean isExpired(RequestPasswordModel forgotPasswordModel) {
        return LocalDateTime.now().isAfter(forgotPasswordModel.getExpireTime());
    }

    public String checkValidity(RequestPasswordDTO forgotPassword, Model model) {

        if (forgotPassword == null) {
            model.addAttribute("error", "Token inválido.");
            return "/default/error-page";
        }

        RequestPasswordModel requestPassword = RequestPasswordDTO.convert(forgotPassword);

        if (requestPassword.isUsed()) {
            model.addAttribute("error", "O token já foi utilizado.");
            return "/default/error-page";
        } else if (isExpired(requestPassword)) {
            model.addAttribute("error", "O token está expirado.");
            return "/default/error-page";
        } else {
            return "/default/reset-password";
        }

    }

}
