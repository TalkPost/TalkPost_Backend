package com.kjo.talkpost.member.entity;

import jakarta.persistence.*;

import com.kjo.talkpost.common.BaseEntity;
import com.kjo.talkpost.member.entity.enums.SocialType;
import com.kjo.talkpost.member.entity.valid.Email;
import com.kjo.talkpost.member.entity.valid.Password;

import lombok.*;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Embedded
  @Column(name = "email", length = 40, nullable = false)
  private Email email;

  @Embedded
  @Column(name = "password", nullable = false)
  private Password password;

  @Column(name = "nickname", length = 15, nullable = false)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(name = "socialType", nullable = false)
  private SocialType socialType;
}
