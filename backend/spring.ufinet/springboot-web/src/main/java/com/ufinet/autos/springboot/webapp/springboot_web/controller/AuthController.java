package com.ufinet.autos.springboot.webapp.springboot_web.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ufinet.autos.springboot.webapp.springboot_web.dto.LoginRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.dto.RegisterRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.dto.TokenResponse;
import com.ufinet.autos.springboot.webapp.springboot_web.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request) {
        final TokenResponse token = service.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody final LoginRequest request) {
        final TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return service.refreshToken(authHeader);
    }
}



















    



