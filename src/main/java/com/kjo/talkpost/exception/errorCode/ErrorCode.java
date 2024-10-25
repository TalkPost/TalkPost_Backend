package com.kjo.talkpost.exception.errorCode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  HttpStatus getStatus();

  String getMessage();

  String getCode();
}
