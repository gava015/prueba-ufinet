package com.ufinet.autos.springboot.webapp.springboot_web.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
    @JsonProperty("access_token")
    String accessToken,
    
    @JsonProperty("refresh_token")
    String refreshToken
) {
}

