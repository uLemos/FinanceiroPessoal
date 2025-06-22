package com.financeiro.backend.infrastructure.config;

import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
  
  private final JwtUtil jwtUtil;

  public JwtFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    String token = null;
    String email = null;

    if(authHeader != null && authHeader.startsWith("Bearer ")){
      // token = authHeader.substring(7);
      token = authHeader.replaceAll("[B,b]earer\\s", "").trim();
      try {
        if(jwtUtil.validarToken(token))
        email = jwtUtil.extrairEmail(token);
      } catch (Exception e) {
        // TODO: handle exception
      }
    }

    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
      UsernamePasswordAuthenticationToken authToken = 
      new UsernamePasswordAuthenticationToken(
        new User(email, "", Collections.emptyList()),
        null,
        Collections.emptyList()                  
      );

      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }
}
