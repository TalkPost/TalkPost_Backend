package com.kjo.talkpost.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode400 implements ErrorCode {
  PASSWORD_NOT_VALID(
      HttpStatus.BAD_REQUEST, "비밀번호는 영어, 숫자, 특수문자를 하나 이상 포함해야 하며, 8자에서 12자 사이여야 합니다."),
  EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
  NICKNAME_NOT_VALID(HttpStatus.BAD_REQUEST, "닉네임은 특수문자를 제외한 최대 10자 입니다,"),
  PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, " 비밀번호가 일치하지 않습니다."),
  MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
  TITLE_NOT_VALID(HttpStatus.BAD_REQUEST, "제목은 최대 50자까지 필수 입력 사항입니다."),
  CONTENT_NOT_VALID(HttpStatus.BAD_REQUEST, "본문은 필수 입력 사항입니다");

  private final HttpStatus status;
  private final String message;

  @Override
  public String getCode() {
    return this.name();
  }
}
