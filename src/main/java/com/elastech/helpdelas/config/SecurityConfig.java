package com.elastech.helpdelas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // marca a classe como configuração para o spring
public class SecurityConfig {

    @Autowired
    private CustomDetailsService customDetailsService;

    @Bean // essa anotação inndica que o método vai retornar um bean que o spring vai gerenciar
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize //para poder configurar como as requisições são autorizadas
                        .requestMatchers( "**").permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build(); // aqui retorna o objeto HttpSecurityConfigurado, para criar a cadeia de filtros de segurança
    }

    // esta método é usado para retornar uma instância da classe e podermos usá-la para criptografar as senhas
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
        
}
