//package com.skcodify.securitylearning.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class InMemorySecurityConfig {
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/auth").permitAll()
//                .requestMatchers("/home").permitAll()
//                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
//                .requestMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().authenticated()
//        );
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("$2a$12$v234twkYjU17nUov5sTdhe6RG09gSwFbPPEfUYeyG2eeMLNfAT0uS")
//                .roles("USER").build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("$2a$12$nj806XXBurRSfHSzvXI68eo7k4wFp1h0INK39mYK8EJstp2gAF6Tm")
//                .roles("ADMIN").build();
//
//
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(user);
//        manager.createUser(admin);
//
//        System.out.println("User: " + user.getPassword());
//        System.out.println("Admin: " + admin.getPassword());
//        return manager;
//    }
//}
