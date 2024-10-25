package com.kjo.talkpost.exception;

import com.kjo.talkpost.exception.errorCode.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {

  private final ErrorCode errorCode;
}
