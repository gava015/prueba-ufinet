package com.ufinet.autos.springboot.webapp.springboot_web.infrastructure.framework.controller;

import com.ufinet.autos.springboot.webapp.springboot_web.domain.service.dto.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {


        return ResponseEntity.ok().build();
    }
}