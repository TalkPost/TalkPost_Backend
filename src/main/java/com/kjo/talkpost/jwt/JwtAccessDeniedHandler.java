package com.kjo.talkpost.jwt;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjo.talkpost.common.GlobalResponse;
import com.kjo.talkpost.exception.errorCode.ErrorCode403;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    response.setContentType("application/json; charset=UTF-8");
    response.setStatus(HttpStatus.FORBIDDEN.value());

    GlobalResponse<Object> error = GlobalResponse.onFailure(ErrorCode403.ACCESS_DENIED);

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), error);
  }
}
