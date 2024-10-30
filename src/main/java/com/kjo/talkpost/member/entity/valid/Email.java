package com.kjo.talkpost.member.entity.valid;

import java.util.regex.Pattern;

import jakarta.persistence.Embeddable;

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
public class Email {

  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  private String validatedEmail;

  public static Email validateEmail(String inputEmail) {
    if (!Pattern.matches(EMAIL_REGEX, inputEmail)) {
      throw new GlobalException(ErrorCode400.EMAIL_NOT_VALID);
    }
    return new Email(inputEmail);
  }
}
