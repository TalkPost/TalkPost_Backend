package com.kjo.talkpost.member.entity.valid;

import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode400;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

  private static final String PWD_REGEX =
      "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{9,15}$";

  @Column(name = "password", nullable = false, columnDefinition = "TEXT")
  private String encryptedPassword;

  public static Password encrypt(String inputPassword, BCryptPasswordEncoder encoder) {
    if (!Pattern.matches(PWD_REGEX, inputPassword)) {
      throw new GlobalException(ErrorCode400.PASSWORD_NOT_VALID);
    }
    return new Password(encoder.encode(inputPassword));
  }

  public boolean isSamePassword(String inputPassword, BCryptPasswordEncoder encoder) {
    return encoder.matches(inputPassword, encryptedPassword);
  }
}
