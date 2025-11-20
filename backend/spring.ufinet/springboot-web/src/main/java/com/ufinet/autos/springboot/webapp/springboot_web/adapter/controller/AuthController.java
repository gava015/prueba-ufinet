package com.ufinet.autos.springboot.webapp.springboot_web.adapter.controller;

import com.ufinet.autos.springboot.webapp.springboot_web.adapter.dto.LoginRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.adapter.dto.RegisterRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.adapter.dto.TokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request) {
        //final TokenResponse token = service.register(request);
        //return ResponseEntity.ok(token);
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody final LoginRequest request) {
        //final TokenResponse token = service.login(request);
        //return ResponseEntity.ok(token);
        return null;
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
       // return service.refreshToken(authHeader);
        return null;
    }
}
