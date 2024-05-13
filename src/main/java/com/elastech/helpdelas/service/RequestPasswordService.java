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

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestPasswordService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RequestPasswordRepository requestPasswordRepository;

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
        requestPasswordRepository.save(forgotPassword);
        return new RequestPasswordDTO(forgotPassword);
    }

    public RequestPasswordDTO getRequestByToken(String token) {
       Optional<RequestPasswordModel> request = requestPasswordRepository.findByToken(token);
       if(request.isEmpty()){
           return null;
       }
        return new RequestPasswordDTO(request.get());
    }

    public void resetPassword(RequestPasswordDTO request, String password) {
        UserModel user = request.getUser();
        user.setPassword(passwordEncoder.encode(password));
        request.setUsed(true);
        userRepository.save(user);
        RequestPasswordModel requestModel = RequestPasswordDTO.convert(request);
        requestPasswordRepository.save(requestModel);
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
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);
    }

    public boolean isExpired(RequestPasswordDTO forgotPassword) {
        return LocalDateTime.now().isAfter(forgotPassword.getExpireTime());
    }

}
