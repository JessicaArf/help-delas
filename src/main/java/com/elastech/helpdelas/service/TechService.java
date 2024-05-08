package com.elastech.helpdelas.service;

import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.RoleRepository;
import com.elastech.helpdelas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.elastech.helpdelas.dtos.UserDTO;

import javax.swing.text.html.Option;
import java.util.Optional;

import static com.elastech.helpdelas.dtos.UserDTO.convert;

@Service
public class TechService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO registerTech(UserDTO userDTO) throws Exception {
        //Aqui está pegando a role de Tech pelo metodo findByName e salva dentro da variavel roleTech
        RoleModel roleTech = roleRepository.findByName(RoleModel.Values.TECH.name());
        //Aqui tesmo um user model optional pois pode ser que não encontre um usuário no BD
        Optional<UserModel> userExists = userRepository.findByEmail(userDTO.getEmail());
        //Condição para verificar se o usuário já exite lançando um mensagem
        if(userExists.isPresent()){
            throw new Exception("Esse e-mail já existe");
        }
        //Pegando a senha do usuário que está sendo cadastrado e passando um metodo de criptografar
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //Passando a role de Tech para esse usuario
        userDTO.setRole(roleTech);
        //Utilizando o metodo de converter o userDTO em um userModel
        UserModel userTech = UserDTO.convert(userDTO);
        //Salvando
        userRepository.save(userTech);
        return new UserDTO(userTech);
    }

}
