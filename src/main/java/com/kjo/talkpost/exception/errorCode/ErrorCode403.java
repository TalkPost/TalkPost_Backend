package com.kjo.talkpost.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode403 implements ErrorCode {
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근이 거부되었습니다.");

  private final HttpStatus status;
  private final String message;

  @Override
  public String getCode() {
    return this.name();
  }
}
