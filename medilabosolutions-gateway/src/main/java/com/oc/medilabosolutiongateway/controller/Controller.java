package com.oc.medilabosolutiongateway.controller;

import com.oc.medilabosolutiongateway.model.User;
import com.oc.medilabosolutiongateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @PostMapping("/gateway/connect")
    public ResponseEntity<String> connect(@RequestBody User user) {

//        userRepository.addUser(user);

//        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);

//        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

        if (true) {
            return ResponseEntity.ok("Authentication successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/gateway/isuserauthenticated")
    public ResponseEntity<Boolean> isUserAuthenticated(@RequestBody User user) {
        return ResponseEntity.ok(true);
//        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
    }
}