package com.dushanz.bookmanager.controller;

import com.dushanz.bookmanager.dto.security.AuthTokenDTO;
import com.dushanz.bookmanager.service.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthorizationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/token")
    public ResponseEntity<AuthTokenDTO> generateToken(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            String token = authenticationService.generateToken(username);
            String expiryDate = authenticationService.getTokenExpiryDate();
            AuthTokenDTO tokenDTO = new AuthTokenDTO(token, "Success", "Created", expiryDate);
            return ResponseEntity.ok(tokenDTO);
        } else {
            AuthTokenDTO tokenDTO = new AuthTokenDTO(null, "Error", "Authentication Failed", "");
            return new ResponseEntity<>(tokenDTO, HttpStatus.UNAUTHORIZED);
        }
    }

}
