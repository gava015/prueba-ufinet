package com.ufinet.autos.springboot.webapp.springboot_web.service;

import java.sql.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ufinet.autos.springboot.webapp.springboot_web.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private String jwtExpiration;
    @Value("${jwt.refresh-token}")
    private String refreshExpiration;

       public JwtService() {
        // Constructor vacío requerido por Spring
    }

    public String extractUsername(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return  jwtToken.getSubject();      
    }

    public String generateToken(final User user){
        return buildToken(user,jwtExpiration); 
    }

    public String generateRefreshToken(final User user){
        return buildToken(user,refreshExpiration);
    }

    
    private String buildToken(final User user, final String expiration){

        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("name", user.getUsername()))
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
   //             .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();

    }  

    public boolean isTokenValid(final String token, User user){
        final String username= extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(final String token){
        return extractExpiration(token).before(new Date()));
    }

    private Date extracExpiration(final String token){
        final Claims jwtToken = Jwt.parser()
                
    }




    
    private SecretKey getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

}
