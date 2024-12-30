package com.skcodify.securitylearning.config;

import com.skcodify.securitylearning.filters.JwtRequestFilter;
import com.skcodify.securitylearning.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class JwtSecurityConfig {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final JwtRequestFilter jwtRequestFilter;
    private final CustomUserDetailsService customUserDetailsService;


    public JwtSecurityConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        log.info("securityFilterChain(-)");
        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/auth").permitAll()
                .requestMatchers("/home").permitAll()
                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return customUserDetailsService;
    }

}
