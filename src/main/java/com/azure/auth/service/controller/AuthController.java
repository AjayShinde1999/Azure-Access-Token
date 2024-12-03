package com.azure.auth.service.controller;

import com.azure.auth.service.dto.TokenResponse;
import com.azure.auth.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getToken() {
        return ResponseEntity.ok(authService.getToken());
    }
}
