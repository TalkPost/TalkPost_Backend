package com.kjo.talkpost.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberRequestDto {

  @Getter
  @Builder
  public static class SignUpRequest {
    String email;
    String password;
    String nickname;
  }
}
