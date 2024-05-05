package com.elastech.helpdelas.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

// essa classe será responsável por personalizar o comportamento após um usuário ser autenticado com sucesso.
@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    /* Esta anotação indica que o método onAuthenticationSuccess substitui o método da
     interface AuthenticationSuccessHandler */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
/* HttpServletRequest representa a requisição HTTP que resultou na autenticação bem sucedida
HttpServletResponse é usado para enviar a resposta HTTP adequada
Authentication contém as informações do usuário autenticado (email e senha) e a sua autoridade (nossa role)
 */
        /* Esta linha recupera as autoridades (papéis) do usuário autenticado a partir do objeto authentication.
        O resultado é armazenado na variável authorities, que é um Set (conjunto) de objetos GrantedAuthority.
         */

        // aqui a gente tá pegando a role do usuário autenticado e estamos salvando no var auhtorities
        var authourities = authentication.getAuthorities();
        var roles = authourities.stream().map(GrantedAuthority::getAuthority).findFirst();

        // verifica o tipo de role e redireciona
        if (roles.orElse("").equals("USER")){
            response.sendRedirect("user/dashboard-user");
        } else if (roles.orElse("").equals("TECH")){
            response.sendRedirect("/dashboard-tecnico");
        } else if (roles.orElse("").equals("ADMIN")){
            response.sendRedirect("/dashboard-admin");
        }
    }
}
