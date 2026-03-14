package com.chat.gusta.securaty;

import com.chat.gusta.util.InternalKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                // Configuração de CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Desabilita CSRF
                .csrf(csrf -> csrf.disable())
                // Configuração de autorização
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/h2-console/**",
                                "/loginadmin/**"
                                ,"/api/qr/**",
                                "/admin/webhook"
                                ,"/admin/mensagens"
                                ,"/admin/agendar"
                        ).permitAll()
                        // Rotas de admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/Usuario/**").authenticated()
                        // Rotas de cadastro
                        .requestMatchers("/Cadastro/**").hasRole("ADMIN")
                        // Rotas de conta (qualquer usuário autenticado)
                        .requestMatchers("/conta/**").authenticated()
                        // Qualquer outra rota precisa estar autenticada
                        .anyRequest().authenticated()
                )
                // Stateless (JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Adiciona filtro JWT antes do UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(internalKeyFilter(), UsernamePasswordAuthenticationFilter.class)
                // Para poder abrir H2 console
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://localhost:8080"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public InternalKeyFilter internalKeyFilter() {
        return new InternalKeyFilter();
    }
}