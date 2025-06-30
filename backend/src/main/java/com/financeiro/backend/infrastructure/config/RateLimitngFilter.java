package com.financeiro.backend.infrastructure.config;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitngFilter implements Filter {

  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String ip = httpRequest.getRemoteAddr();
    Bucket bucket = buckets.computeIfAbsent(ip, this::criarBucket);

    if(bucket.tryConsume(1))
      chain.doFilter(request, response);
    else{
      httpResponse.setStatus(429);
      httpResponse.getWriter().write("Voce excedeu o limite de requisicoes. Tente novamente mais tarde.");
    }
  }

  private Bucket criarBucket(String ip){
    Refill refill = Refill.greedy(100, Duration.ofMinutes(1));
    Bandwidth limit = Bandwidth.classic(100, refill);
    return Bucket.builder().addLimit(limit).build();
  }
}

