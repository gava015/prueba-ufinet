package com.ufinet.autos.springboot.webapp.springboot_web.config;

import java.io.IOException;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ufinet.autos.springboot.webapp.springboot_web.model.User;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.Token;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.TokenRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.repository.UserRepository;
import com.ufinet.autos.springboot.webapp.springboot_web.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{ //-> cada vez que se hace una petición

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException{

        if(request.getServletPath().contains("/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(7);
        final String fullName = jwtService.extractUsername(jwtToken);
        if(fullName == null || SecurityContextHolder.getContext().getAuthentication() != null){
           return;
        }

        final Token token = tokenRepository.findByToken(jwtToken)
                 .orElse(null);
        if (token == null || token.isExpired() || token.isRevoked()){
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(fullName);
        final Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if(user.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        final boolean isTokenValid = jwtService.isTokenValid(jwtToken, user.get());
        if (!isTokenValid){
            return;
        }

        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                        null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }  
}
