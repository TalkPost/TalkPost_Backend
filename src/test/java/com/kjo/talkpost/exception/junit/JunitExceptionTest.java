package com.kjo.talkpost.exception.junit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kjo.talkpost.exception.ExceptionService;
import com.kjo.talkpost.exception.GlobalException;

@SpringBootTest
public class JunitExceptionTest {

  @Autowired private ExceptionService service;

  @Test
  void exceptionTest404() {
    assertThrows(GlobalException.class, () -> service.triggerErrorCode404());
  }

  @Test
  void exceptionTest403() {
    assertThrows(GlobalException.class, () -> service.triggerErrorCode403());
  }

  @Test
  void exceptionTes401t() {
    assertThrows(GlobalException.class, () -> service.triggerErrorCode401());
  }

  @Test
  void exceptionTest400() {
    assertThrows(GlobalException.class, () -> service.triggerErrorCode400());
  }

  @Test
  void exceptionTest200() {
    assertThrows(GlobalException.class, () -> service.triggerErrorCode200());
  }
}
