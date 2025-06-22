package com.financeiro.backend.infrastructure.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.financeiro.backend.domain.entitys.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtExpirationMs;

  public Key getSigningKey(){
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String gerarToken(Usuario usuario) {
    return Jwts.builder()
            .setSubject(usuario.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
  }
  
  public String extrairEmail(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public boolean validarToken(String token){
    try{
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    }catch(JwtException | IllegalArgumentException e){
      return false;
    }
  }
}
