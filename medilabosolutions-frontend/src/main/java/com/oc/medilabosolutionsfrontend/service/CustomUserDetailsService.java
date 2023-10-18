//package com.oc.medilabosolutionsfrontend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private ProxyService backendUserService;
//
//    public UserDetails loadUserByUsername(String username) {
//        Optional<User> oUser = userRepository.findByUsername(username);
//        if(oUser.isEmpty()) {
//            throw new UsernameNotFoundException("User " +username+ " not found");
//        }
//        return new org.springframework.security.core.userdetails.User(oUser.get().getUsername(), oUser.get().getPassword(), Collections.singletonList(new SimpleGrantedAuthority(oUser.get().getRole())));
//    }
//}
