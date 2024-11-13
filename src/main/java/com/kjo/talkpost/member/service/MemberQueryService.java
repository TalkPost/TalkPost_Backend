package com.kjo.talkpost.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode404;
import com.kjo.talkpost.member.entity.Member;
import com.kjo.talkpost.member.entity.valid.Email;
import com.kjo.talkpost.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

  private final MemberRepository memberRepository;

  public Member getMemberByEmail(Email email) {
    return memberRepository
        .findByEmail(email)
        .orElseThrow(() -> new GlobalException(ErrorCode404.MEMBER_NOT_FOUND));
  }

  public Member getMemberById(Long memberId) {
    return memberRepository
        .findById(memberId)
        .orElseThrow(() -> new GlobalException(ErrorCode404.MEMBER_NOT_FOUND));
  }

  public void isMemberExist(Email email) {
    if (memberRepository.findByEmail(email).isPresent()) {
      throw new GlobalException(ErrorCode404.MEMBER_NOT_FOUND);
    }
  }
}
