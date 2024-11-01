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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member API", description = "Member API")
@RequestMapping("/api/v1/members")
public class MemberController {

  private final MemberCommandService memberCommandService;

  @Operation(summary = "회원가입 API", description = "회원가입 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/signup")
  public GlobalResponse<SignUpResponse> signUp(@RequestBody SignUpRequest req) {
    return GlobalResponse.onSuccess(
        ErrorCode200.CREATED, MemberConverter.toSignUpResponse(memberCommandService.signUp(req)));
  }

  @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping("/login")
  public GlobalResponse<TokenResponse> loginMember(@RequestBody LoginRequest request) {
    return GlobalResponse.onSuccess(ErrorCode200.CREATED, memberCommandService.login(request));
  }

  @Operation(summary = "reissue API", description = "토큰 재발급 API 입니다.")
  @ApiResponse(responseCode = "200", description = "성공")
  @PostMapping("/reissue")
  public GlobalResponse<TokenResponse> reissue(@RequestBody ReissueRequest request) {
    return GlobalResponse.onSuccess(memberCommandService.reissue(request));
  }
}
