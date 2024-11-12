package com.kjo.talkpost.post.dto;

import lombok.Builder;
import lombok.Getter;

public class PostRequestDto {

  @Getter
  @Builder
  public static class CreatePostRequest {
    String title;
    String postContent;
  }

  @Getter
  @Builder
  public static class UpdatePostRequest {
    String title;
    String postContent;
  }
}
