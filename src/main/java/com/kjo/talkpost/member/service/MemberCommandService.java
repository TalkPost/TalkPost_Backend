package com.kjo.talkpost.member.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kjo.talkpost.converter.MemberConverter;
import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode400;
import com.kjo.talkpost.exception.errorCode.ErrorCode401;
import com.kjo.talkpost.jwt.JwtProvider;
import com.kjo.talkpost.member.dto.MemberRequestDto.*;
import com.kjo.talkpost.member.dto.MemberResponseDto.*;
import com.kjo.talkpost.member.entity.Member;
import com.kjo.talkpost.member.entity.valid.Email;
import com.kjo.talkpost.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder encoder;
  private final JwtProvider jwtProvider;
  private final RedisTemplate<String, String> redisTemplate;
  private final MemberQueryService memberQueryService;

  @Value("${jwt.refresh-token-validity}")
  private Long refreshTokenValidityMilliseconds;

  public Member signUp(SignUpRequest req) {
    validMemberExists(Email.validateEmail(req.getEmail()));
    return memberRepository.save(MemberConverter.toMember(req));
  }

  public TokenResponse login(LoginRequest req) {
    Member member = memberQueryService.getMemberByEmail(Email.validateEmail(req.getEmail()));

    if (!(member.getPassword().isSamePassword(req.getPassword(), encoder))) {
      throw new GlobalException(ErrorCode400.PASSWORD_MISMATCH);
    }

    String accessToken = jwtProvider.generateAccessToken(member.getMemberId());
    String refreshToken = jwtProvider.generateRefreshToken(member.getMemberId());

    return MemberConverter.toLoginResponse(member.getMemberId(), accessToken, refreshToken);
  }

  public TokenResponse reissue(ReissueRequest request) {
    String refreshToken = request.getRefreshToken();

    Long memberId = jwtProvider.parseRefreshToken(refreshToken);

    if (!refreshToken.equals(redisTemplate.opsForValue().get(String.valueOf(memberId)))) {
      throw new GlobalException(ErrorCode401.INVALID_TOKEN);
    }

    Member member = memberQueryService.getMemberById(memberId);

    String newAccessToken = jwtProvider.generateAccessToken(member.getMemberId());
    String newRefreshToken = jwtProvider.generateRefreshToken(member.getMemberId());

    redisTemplate
        .opsForValue()
        .set(
            String.valueOf(member.getMemberId()),
            newRefreshToken,
            refreshTokenValidityMilliseconds,
            TimeUnit.MILLISECONDS);

    return MemberConverter.toReissueTokenResponse(
        member.getMemberId(), newAccessToken, newRefreshToken);
  }

  private void validMemberExists(Email email) {
    if (memberQueryService.isMemberExist(email)) {
      throw new GlobalException(ErrorCode400.MEMBER_ALREADY_EXISTS);
    }
  }
}
