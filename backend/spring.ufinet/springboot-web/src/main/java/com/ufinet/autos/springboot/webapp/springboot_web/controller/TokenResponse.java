package com.ufinet.autos.springboot.webapp.springboot_web.controller;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
    @JsonProperty("access_token")
    String accessToken,
    
    @JsonProperty("refresh_token")
    String refreshToken
) {
}

