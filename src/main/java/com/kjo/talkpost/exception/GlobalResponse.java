package com.kjo.talkpost.exception;

import java.time.LocalDateTime;

import com.kjo.talkpost.exception.errorCode.*;

import lombok.Getter;

@Getter
public class GlobalResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private final Integer status;
  private final String error;
  private final String code;
  private final String message;

  public GlobalResponse(ErrorCode errorCode) {
    this.status = errorCode.getStatus().value();
    this.error = errorCode.getStatus().name();
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }
}
