package com.elastech.helpdelas.service;

import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.model.RoleModel;
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

    public UserModel salvar(UserModel userModel) throws Exception {

        Optional<UserModel> byEmail = userRepository.findByEmail(userModel.getEmail());
        if(!byEmail.isPresent()){
            RoleModel role = roleRepository.findByName(RoleModel.Values.USER.name());
            userModel.setRole(role);
            userModel.setSector(userModel.getSector());
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
            return userRepository.save(userModel);
        } else {
            throw new Exception("Já existe um cliente cadastrado com esse e-mail.");
        }
    }

    public List<SectorDTO> findAllSector(){
        return sectorService.findAll();
    }

    public List<UserModel> userModelList(UserModel filtroUser){
        //metodo que permite buscar por todos os dados da base da table especifico bem como filtrar por qualquer coluna que contenha  na
        //base de dados, ignorando letras maiuscula/minuscula
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtroUser, matcher); //pegar as propriedas populadas e criar o objeto
        return userRepository.findAll(example);
    }
    /*public UserModel updateUserById(Long id, UserModel userModel){

    }*/

    public UserModel userFindById(Long id){
        return userRepository.findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Usuário não encontrado"));

    }

    public UserModel delete(Long id){
        return userRepository.findById(id)
                .map( usuario -> {
                    userRepository.delete(usuario);
                    return usuario;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public UserModel find(String user) {
        return userRepository.findByName(user);
    }
}
