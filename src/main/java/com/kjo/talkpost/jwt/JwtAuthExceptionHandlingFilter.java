package com.kjo.talkpost.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjo.talkpost.common.GlobalResponse;
import com.kjo.talkpost.exception.GlobalException;

@Component
public class JwtAuthExceptionHandlingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (GlobalException e) {
      response.setContentType("application/json; charset=UTF-8");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());

      GlobalResponse<Object> error = GlobalResponse.onFailure(e.getErrorCode(), null);

      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getOutputStream(), error);
    }
  }
}
