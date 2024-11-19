package com.kjo.talkpost.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode401 implements ErrorCode {
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
  AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.");

  private final HttpStatus status;
  private final String message;

  @Override
  public String getCode() {
    return "401: " + this.name();
  }
}
