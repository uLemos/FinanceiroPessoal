package com.financeiro.backend.infrastructure.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.financeiro.backend.application.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
  
  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;

  public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
    this.jwtUtil = jwtUtil;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
        
    final String authHeader = request.getHeader("Authorization");

    String token = null;
    String email = null;

    if(authHeader != null && authHeader.startsWith("Bearer ")){
      token = authHeader.substring(7);
      try {
        email = jwtUtil.extrairEmail(token);
      } catch (Exception e) {
        System.out.println("Token inválido ou expirado: " + e.getMessage());
        filterChain.doFilter(request, response);
        return;
      }
    }

    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
      
      if(jwtUtil.validarToken(token)){
        UsernamePasswordAuthenticationToken authToken = 
        new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()                 
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
     }
    }
    filterChain.doFilter(request, response);
  }
}
