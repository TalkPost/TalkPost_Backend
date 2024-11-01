package com.kjo.talkpost.member.service;

import java.util.Optional;
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
import com.kjo.talkpost.exception.errorCode.ErrorCode404;
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

  @Value("${jwt.refresh-token-validity}")
  private Long refreshTokenValidityMilliseconds;

  public Member signUp(SignUpRequest req) {
    Optional<Member> member = memberRepository.findByEmail(Email.validateEmail(req.getEmail()));

    if (member.isPresent()) {
      throw new GlobalException(ErrorCode400.MEMBER_ALREADY_EXISTS);
    }

    return memberRepository.save(MemberConverter.toMember(req));
  }

  public TokenResponse login(LoginRequest req) {
    Member member =
        memberRepository
            .findByEmail(Email.validateEmail(req.getEmail()))
            .orElseThrow(() -> new GlobalException(ErrorCode404.MEMBER_NOT_FOUND));

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

    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode404.MEMBER_NOT_FOUND));

    String newAccessToken = jwtProvider.generateAccessToken(member.getMemberId());
    String newRefreshToken = jwtProvider.generateRefreshToken(member.getMemberId());

    redisTemplate
        .opsForValue()
        .set(
            String.valueOf(member.getMemberId()),
            newRefreshToken,
            refreshTokenValidityMilliseconds,
            TimeUnit.MILLISECONDS);

    return MemberConverter.toReissueTokenResponse(memberId, newAccessToken, newRefreshToken);
  }
}
