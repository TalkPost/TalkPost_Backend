package com.kjo.talkpost.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kjo.talkpost.converter.MemberConverter;
import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode400;
import com.kjo.talkpost.member.dto.MemberRequestDto.*;
import com.kjo.talkpost.member.entity.Member;
import com.kjo.talkpost.member.entity.valid.Email;
import com.kjo.talkpost.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCommandService {

  private final MemberRepository memberRepository;

  public Member signUp(SignUpRequest req) {
    Optional<Member> member = memberRepository.findByEmail(Email.validateEmail(req.getEmail()));

    if (member.isPresent()) {
      throw new GlobalException(ErrorCode400.MEMBER_ALREADY_EXISTS);
    }

    return memberRepository.save(MemberConverter.toMember(req));
  }
}
