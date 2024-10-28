package com.kjo.talkpost.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kjo.talkpost.exception.errorCode.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class GlobalResponse<T> {

  private static final String SUCCESS_CODE = "200";
  private static final String SUCCESS_MESSAGE = "요청에 성공하였습니다";

  @JsonProperty("isSuccess")
  private Boolean isSuccess;

  private String code;
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  public static <T> GlobalResponse<T> onSuccess(T data) {
    return new GlobalResponse<>(true, SUCCESS_CODE, SUCCESS_MESSAGE, data);
  }

  public static <T> GlobalResponse<T> onSuccess(ErrorCode code, T data) {
    return new GlobalResponse<>(
        true, String.valueOf(code.getStatus().value()), code.getMessage(), data);
  }

  public static <T> GlobalResponse<T> onFailure(ErrorCode code, T data) {
    return new GlobalResponse<>(
        false, String.valueOf(code.getStatus().value()), code.getMessage(), data);
  }

  public static <T> GlobalResponse<T> onFailure(ErrorCode code) {
    return new GlobalResponse<>(
        false, String.valueOf(code.getStatus().value()), code.getMessage(), null);
  }
}
