package com.skcodify.securitylearning.controllers;

import com.skcodify.securitylearning.dto.AuthenticationRequest;
import com.skcodify.securitylearning.dto.AuthenticationResponse;
import com.skcodify.securitylearning.exceptions.InvalidUserException;
import com.skcodify.securitylearning.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
public class AuthController {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth")
    public AuthenticationResponse createToken(@RequestBody AuthenticationRequest request) throws InvalidUserException {
        log.info("createToken(-)");
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if(!request.getPassword().equals(userDetails.getPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid User");
        }
        String jwtToken = jwtUtil.generateToken(request.getUsername());
        return new AuthenticationResponse(jwtToken);
    }
}
