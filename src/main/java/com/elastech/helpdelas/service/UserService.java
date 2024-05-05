package com.elastech.helpdelas.service;

import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel salvar(UserModel userModel){
        return userRepository.save(userModel);
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
}
