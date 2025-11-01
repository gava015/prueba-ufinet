package com.ufinet.autos.springboot.webapp.springboot_web.service;

import org.springframework.stereotype.Service;

import com.ufinet.autos.springboot.webapp.springboot_web.controller.LoginRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.controller.RegisterRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.controller.TokenResponse;
import com.ufinet.autos.springboot.webapp.springboot_web.model.User;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.Token;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponse register(RegisterRequest request){
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        var savedUser = UserRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);

    }


    public TokenResponse login(LoginRequest request){
        return null;
    }

    private void saveUserToken(User user, String jwtToken){
        var token = Token.Builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);

    }

    public TokenResponse refreshToken(final String authHeader){
        return null;
    }
    
}
