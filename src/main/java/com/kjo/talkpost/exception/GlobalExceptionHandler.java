package com.kjo.talkpost.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {GlobalException.class})
  protected ResponseEntity<GlobalResponse> handleGlobalException(final GlobalException e) {
    log.error("handleGlobalException: {}", e.getErrorCode());
    return ResponseEntity.status(e.getErrorCode().getStatus().value())
        .body(new GlobalResponse(e.getErrorCode()));
  }
}
