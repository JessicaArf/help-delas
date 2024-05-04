package com.elastech.helpdelas.config;

import com.elastech.helpdelas.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// Esta classe é responsável por fornecer detalhes do usuário ao Spring Security para fins de autenticação.
public class CustomUserDetails implements UserDetails {

    private final UserModel user;

    public CustomUserDetails(UserModel user){
        this.user = user;
    }

    /* Este método sobrescreve o método getAuthorities da interface UserDetails.
       É responsável por retornar uma coleção de objetos GrantedAuthority representando as autoridades (funções) do usuário.
       O Spring Security usa essas autoridades para determinar quais ações o usuário está autorizado a realizar.
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>();
        // Obtém as autoridades da Role associada ao usuário
        if (user.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        }
        return authorities;
    }


    // Este método sobrescreve o método getPassword da interface UserDetails. É responsável por retornar a senha do usuário para autenticação.
    @Override
    public String getPassword() {
        return user.getPassword(); // Essa linha recupera a senha do objeto user e a retorna. O Spring Security compara essa senha com a fornecida pelo usuário durante o login.
    }


    //Este método sobrescreve o método getUsername da interface UserDetails. É responsável por retornar o nome de usuário (ou neste caso, endereço de e-mail) do usuário para autenticação.
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // Este método verifica se a conta do usuário está expirada. Uma conta expirada significa que o usuário não tem mais permissão para acessar o sistema após um determinado período de tempo.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Este método verifica se a conta do usuário está bloqueada. Uma conta bloqueada significa que o usuário não pode mais fazer login no sistema, geralmente devido a tentativas excessivas de login falhas.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Este método verifica se as credenciais do usuário (senha) estão expiradas. Credenciais expiradas significam que a senha do usuário precisa ser alterada após um determinado período de tempo.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Este método verifica se a conta do usuário está ativada. Uma conta desativada significa que o usuário não tem permissão para acessar o sistema até que seja ativada por um administrador.
    @Override
    public boolean isEnabled() {
        return true;
    }

}
