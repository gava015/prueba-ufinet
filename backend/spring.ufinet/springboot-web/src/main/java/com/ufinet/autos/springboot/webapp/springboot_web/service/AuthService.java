package com.ufinet.autos.springboot.webapp.springboot_web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ufinet.autos.springboot.webapp.springboot_web.dto.LoginRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.dto.RegisterRequest;
import com.ufinet.autos.springboot.webapp.springboot_web.dto.TokenResponse;
import com.ufinet.autos.springboot.webapp.springboot_web.model.User;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.Token;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.TokenRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.UserRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.exception.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;

    public TokenResponse register(RegisterRequest request){
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateUsernameException("Ese nombre de usuario ya está registrado.");
        }

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
         
            throw new DuplicateUsernameException("Ese nombre de usuario ya está registrado.");
        }

        var jwtToken = jwtService.generateToken(user); 
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponse(jwtToken, refreshToken);

    }

    public TokenResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokentype(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);

    }
    
    public TokenResponse refreshToken(final String authHeader){
        return null;
    }
}
