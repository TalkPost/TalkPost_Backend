package com.kjo.talkpost.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode200 implements ErrorCode {
  CREATED(HttpStatus.CREATED, "요청 성공 및 리소스 생성되었습니다."),
  UPDATED(HttpStatus.ACCEPTED, "요청 성공 및 리소스 수정되었습니다."),
  DELETED(HttpStatus.NO_CONTENT, "요청 성공 및 리소스 삭제되었습니다.");

  private final HttpStatus status;
  private final String message;

  @Override
  public String getCode() {
    return this.name();
  }
}
