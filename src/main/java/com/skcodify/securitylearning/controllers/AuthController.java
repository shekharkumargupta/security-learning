package com.skcodify.securitylearning.controllers;

import com.skcodify.securitylearning.dto.AuthenticationRequest;
import com.skcodify.securitylearning.dto.AuthenticationResponse;
import com.skcodify.securitylearning.exceptions.InvalidCredentialException;
import com.skcodify.securitylearning.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) throws InvalidCredentialException {
        log.info("createToken(-)");
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if(!request.getPassword().equals(userDetails.getPassword())){
            throw new InvalidCredentialException("Invalid credential: " + userDetails.getUsername());
        }
        String jwtToken = jwtUtil.generateToken(request.getUsername());
        return new AuthenticationResponse(jwtToken);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException usernameNotFoundException){
        log.warning("handleUserNotFoundException(-)");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(usernameNotFoundException.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<String> handleInvalidCredentialException(InvalidCredentialException invalidCredentialException){
        log.warning("handleInvalidCredentialException(-)");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(invalidCredentialException.getMessage());
    }
}
