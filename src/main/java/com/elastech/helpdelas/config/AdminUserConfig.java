package com.elastech.helpdelas.config;


import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.RoleRepository;
import com.elastech.helpdelas.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception{

        // salvando a role de admin dentro da variável roleAdmin
        RoleModel roleAdmin = roleRepository.findByName(RoleModel.Values.ADMIN.name());
        // procurando se já existe alguém com esse email de admin
        Optional<UserModel> userAdmin = userRepository.findByEmail("admin@helpdelas.com");
        // caso não encontre um admin dentro do bd vai ser criado um
        userAdmin.ifPresentOrElse(
                user ->  {
                    System.out.println("admin ja existe");
                }, () ->{
                    var user = new UserModel();
                    user.setEmail("admin@helpdelas.com");
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setName("Admin");
                    user.setRole(roleAdmin);
                    userRepository.save(user);
                }
        );

    }
}

