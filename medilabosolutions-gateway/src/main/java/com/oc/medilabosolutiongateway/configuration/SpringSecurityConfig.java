//package com.oc.medilabosolutiongateway.configuration;
//
//import com.oc.medilabosolutiongateway.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SpringSecurityConfig {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    //Password Encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    //Login Security configuration
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/frontend/login","/frontend/home").permitAll()
//                        .requestMatchers("/frontend/home").hasAnyRole("USER")
//                        .anyRequest().authenticated()
//                )
//
//                .formLogin((form) -> form
//                        .loginPage("/frontend/login")
//                        .permitAll()
//                        .defaultSuccessUrl("/frontend/home"))
//
//                .logout((logout) -> logout
//                        .logoutUrl("/login"))
//
//                .userDetailsService(customUserDetailsService)
//
//                .build();
//    }
//
//}