package com.ufinet.autos.springboot.webapp.springboot_web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ufinet.autos.springboot.webapp.springboot_web.model.User;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(final User user){
        return buildToken(user,jwtExpiration); 
    }

    public String generateRefreshToken(final User user){
        return buildToken(user,refreshExpiration);
    }

    
    public String buildToken(final User user, final long expiration){
        return secretKey; // posible solución
    }  
}
