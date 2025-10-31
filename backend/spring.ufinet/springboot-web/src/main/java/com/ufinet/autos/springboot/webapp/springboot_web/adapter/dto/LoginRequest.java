package com.ufinet.autos.springboot.webapp.springboot_web.adapter.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
) {}
