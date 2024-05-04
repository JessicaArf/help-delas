package com.elastech.helpdelas.config;

import com.elastech.helpdelas.model.UserModel;
import com.elastech.helpdelas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service // para definir essa classe como um service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // injetando o userRepository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username)); // expressão lambda para em caso de não encontrar o usuário
        // Aqui criamos uma SimpleGrantedAuthority com o nome da role do usuário
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        System.out.println(user.getRole().getName());
        // Retornamos um UserDetails com as informações do usuário e sua autoridade (role)
        return new User(user.getEmail(), user.getPassword(), Collections.singleton(authority));
    }

}
