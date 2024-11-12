package com.kjo.talkpost.jwt;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kjo.talkpost.member.entity.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberDetails implements UserDetails {

  private final Member member;

  @Override
  public String getUsername() {
    return member.getMemberId().toString();
  }

  @Override
  public String getPassword() {
    return member.getPassword().toString();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> roles = List.of("ROLE_USER");

    return roles.stream().map(SimpleGrantedAuthority::new).toList();
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
}
