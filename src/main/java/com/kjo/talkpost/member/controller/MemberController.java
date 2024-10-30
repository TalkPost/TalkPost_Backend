package com.kjo.talkpost.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjo.talkpost.common.GlobalResponse;
import com.kjo.talkpost.converter.MemberConverter;
import com.kjo.talkpost.exception.errorCode.ErrorCode200;
import com.kjo.talkpost.member.dto.MemberRequestDto.*;
import com.kjo.talkpost.member.dto.MemberResponseDto.*;
import com.kjo.talkpost.member.service.MemberCommandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberCommandService memberCommandService;

  @PostMapping("/signup")
  public GlobalResponse<SignUpResponse> signUp(@RequestBody SignUpRequest req) {
    return GlobalResponse.onSuccess(
        ErrorCode200.CREATED, MemberConverter.toSignUpResponse(memberCommandService.signUp(req)));
  }
}
