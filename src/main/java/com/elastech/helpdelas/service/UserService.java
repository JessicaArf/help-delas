package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.model.RoleModel;
import com.elastech.helpdelas.model.SectorModel;
import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.RoleRepository;
import com.elastech.helpdelas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public UserDTO save(UserDTO userDTO) throws Exception {
        Optional<UserModel> byEmail = userRepository.findByEmail(userDTO.getEmail());
        if(!byEmail.isPresent()){
            RoleModel role = roleRepository.findByName(RoleModel.Values.USER.name());
            userDTO.setRole(role);
            userDTO.setSector(userDTO.getSector());
            userDTO.setStatus("ATIVO");
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserModel userModel = UserDTO.convert(userDTO);
            userRepository.save(userModel);
            return new UserDTO(userModel);
        } else {
            throw new Exception("Já existe um cliente cadastrado com esse e-mail.");
        }
    }

    public List<SectorDTO> findAllSector(){
        return sectorService.findAllSector();
    }

   public List<UserDTO> findAll(){
       List<UserModel> users = userRepository.findAll();
       return users.stream().map(UserDTO::new).collect(Collectors.toList());
   }

    public UserDTO updateUserById(Long id, UserDTO userDTO) throws Exception {
        Optional<UserModel> userExistente =  userRepository.findById(id);
        if(userExistente.isPresent()){
            UserModel user = userExistente.get();
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            user.setSector(userDTO.getSector());
            user.setSupervisor(userDTO.getSupervisor());
            userRepository.save(user);
            return new UserDTO(user);
        } else {
            return userDTO;
        }
    }

    public UserDTO getUserByEmail(String email){
        Optional<UserModel> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado");
        }
        return new UserDTO(user.get());
    }

    public UserDTO getUserById(Long id){
        Optional<UserModel> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado");
        }
        return new UserDTO(user.get());
    }

    public UserDTO updateStatus(Long id, String status){
        Optional<UserModel> userExistente =  userRepository.findById(id);
        if(userExistente.isPresent()){
            if(status.equals("desativar")){
                UserModel user = userExistente.get();
                user.setStatus("INATIVO");
                userRepository.save(user);
                return new UserDTO(user);
            } else {
                UserModel user = userExistente.get();
                user.setStatus("ATIVO");
                userRepository.save(user);
                return new UserDTO(user);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado");
        }
    }

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


