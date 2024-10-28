package com.kjo.talkpost.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjo.talkpost.member.entity.Member;
import com.kjo.talkpost.member.entity.valid.Email;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(Email email);
}
