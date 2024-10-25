package com.kjo.talkpost.exception;

import org.springframework.stereotype.Service;

import com.kjo.talkpost.exception.errorCode.*;

@Service
public class ExceptionService {

  public void triggerErrorCode200() {
    throw new GlobalException(ErrorCode200.CREATED);
  }

  public void triggerErrorCode400() {
    throw new GlobalException(ErrorCode400.MEMBER_ALREADY_EXISTS);
  }

  public void triggerErrorCode401() {
    throw new GlobalException(ErrorCode401.AUTHENTICATION_REQUIRED);
  }

  public void triggerErrorCode403() {
    throw new GlobalException(ErrorCode403.ACCESS_DENIED);
  }

  public void triggerErrorCode404() {
    throw new GlobalException(ErrorCode404.MEMBER_NOT_FOUND);
  }
}
