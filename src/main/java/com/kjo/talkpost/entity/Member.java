package com.kjo.talkpost.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Column(name = "email", length = 40, nullable = false)
  private String email;

  @Column(name = "password", length = 255, nullable = false)
  private String password;

  @Column(name = "nickname", length = 10, nullable = false)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(name = "socialType", nullable = false)
  private SocialType socialType;
}
