package com.kjo.talkpost.member.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode404;
import com.kjo.talkpost.member.entity.Member;
import com.kjo.talkpost.member.entity.valid.Email;
import com.kjo.talkpost.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
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

  public boolean isMemberExist(Email email) {
    return memberRepository.findByEmail(email).isPresent();
  }
}
