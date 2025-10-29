package com.ufinet.autos.springboot.webapp.springboot_web.infrastructure.framework.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testConnection() {
        return "Conexión a Spring Boot y MySQL OK 🚀";
    }
}   


