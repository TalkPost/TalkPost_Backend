package com.kjo.talkpost.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/exception")
public class ExceptionTestController {

  private final ExceptionService exceptionService;

  @Autowired
  public ExceptionTestController(ExceptionService exceptionService) {
    this.exceptionService = exceptionService;
  }

  @GetMapping("/400")
  public ResponseEntity<Void> triggerError400() {
    // 이 메서드는 예외를 발생시킴
    exceptionService.triggerErrorCode400();
    return ResponseEntity.ok().build();
  }
}
