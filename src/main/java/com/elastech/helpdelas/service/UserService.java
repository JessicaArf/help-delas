package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.RoleRepository;
import com.elastech.helpdelas.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    public void save(UserDTO userDTO) throws Exception {
        Optional<UserModel> byEmail = userRepository.findByEmail(userDTO.getEmail());
        RoleModel role = roleRepository.findByName(RoleModel.Values.USER.name());
        if (!byEmail.isPresent()) {
            userDTO.setRole(role);
            userDTO.setSector(userDTO.getSector());
            userDTO.setStatus("ATIVO");
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserModel userModel = UserDTO.convert(userDTO);
            userRepository.save(userModel);
            sendNewUser(userModel);
        } else {
            throw new Exception("Já existe um usuário cadastrado com esse e-mail.");
        }
    }

    public void saveTech(UserDTO userDTO) throws Exception {
        Optional<UserModel> byEmail = userRepository.findByEmail(userDTO.getEmail());
        RoleModel role = roleRepository.findByName(RoleModel.Values.TECH.name());
        if (!byEmail.isPresent()) {
            userDTO.setRole(role);
            userDTO.setSector(userDTO.getSector());
            userDTO.setStatus("ATIVO");
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserModel userModel = UserDTO.convert(userDTO);
            userRepository.save(userModel);
            sendNewTech(userModel);
        } else {
            throw new RuntimeException("Já existe um usuário cadastrado com esse e-mail.");
        }
    }

    public List<SectorDTO> findAllSector() {
        return sectorService.findAllSector();
    }

    public List<UserDTO> findAll() {
        List<UserModel> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO updateUserById(Long userId, UserDTO userDTO) throws Exception {
        Optional<UserModel> existingUser = userRepository.findById(userId);
        Optional<UserModel> existingEmail = userRepository.findByEmail(existingUser.get().getEmail());
        Boolean userIgnoring = getUserByEmailIgnoringCaseAndUser(userDTO.getEmail(), userId);

        if (userIgnoring) {
            throw new Exception("Já existe um usuário cadastrado com esse e-mail.");
        } else {
            UserModel user = existingEmail.get();
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            if (user.getRole().getName().equals("TECH")) {
                user.setSector(existingUser.get().getSector());
            } else {
                user.setSector(userDTO.getSector());
            }
            user.setSupervisor(userDTO.getSupervisor());
            userRepository.save(user);
            return new UserDTO(user);
        }
    }

    public Boolean getUserByEmailIgnoringCaseAndUser(String email, Long idUser) {
        UserModel byEmailIgnoringCaseAndIdNot = userRepository.findByEmailIgnoringCaseAndUserIdNot(email, idUser);
        if (byEmailIgnoringCaseAndIdNot != null) {
            return true;
        }
        return false;
    }

    public UserDTO getUserByEmail(String email) {
        Optional<UserModel> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return new UserDTO(user.get());
    }

    public UserDTO getUserById(Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return new UserDTO(user.get());
    }

    public void updateStatus(Long id, String status) {
        Optional<UserModel> userExistente = userRepository.findById(id);
        if (userExistente.isPresent()) {
            if (status.equals("desativar")) {
                UserModel user = userExistente.get();
                user.setStatus("INATIVO");
                userRepository.save(user);
                new UserDTO(user);
            } else {
                UserModel user = userExistente.get();
                user.setStatus("ATIVO");
                userRepository.save(user);
            }
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }


    public List<UserDTO> showAllUsersWithSector(Long sectorId) {
        List<UserModel> users = userRepository.findBySectorSectorId(sectorId);
        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllTech() {
        List<UserModel> list = userRepository.findByRoleName("TECH");
        return list.stream()
                .filter(user -> user.getStatus().equals("ATIVO")) // Verifica se o técnico está ativo
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    private void sendEmail(String emailContent, String to, String subject) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setText(emailContent, true);
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);
    }

    private void sendNewUser(UserModel userModel) throws MessagingException, UnsupportedEncodingException {
        String emailContent = "<h1>Bem vindo(a), " + userModel.getName() + "!</h1>" +
                "<p>Seu cadastro foi realizado com sucesso! </p>" +
                "<p>Equipe HelpDelas</p>";
        sendEmail(emailContent, userModel.getEmail(), "Cadastro Realizado Com Sucesso");
    }

    private void sendNewTech(UserModel userModel) throws MessagingException, UnsupportedEncodingException {
        String emailContent = "<h1>Bem vindo(a), " + userModel.getName() + "!</h1>"
                + "<p>Seu cadastro foi realizado em nossa plataforma.</p>"
                + "<p>Usuário: " + userModel.getEmail() + "</p>"
                + "<p>Senha: Welcome </p>"
                + "<p>Equipe HelpDelas</p>";
        sendEmail(emailContent, userModel.getEmail(), "Cadastro Realizado Com Sucesso");
    }

}


