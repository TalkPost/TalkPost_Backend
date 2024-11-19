package com.kjo.talkpost.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.kjo.talkpost.jwt.MemberDetails;
import com.kjo.talkpost.member.entity.Member;

@Component
public class MemberUtil {
  public static Member getCurrentMember() {
    MemberDetails memberDetails =
        (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return memberDetails.getMember();
  }
}
