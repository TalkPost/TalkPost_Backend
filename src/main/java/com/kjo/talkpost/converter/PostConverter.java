package com.kjo.talkpost.converter;

import org.springframework.stereotype.Component;

import com.kjo.talkpost.member.entity.Member;
import com.kjo.talkpost.post.dto.PostRequestDto.*;
import com.kjo.talkpost.post.dto.PostResponseDto.*;
import com.kjo.talkpost.post.entity.Post;

@Component
public class PostConverter {

  public static Post toPost(CreatePostRequest req, Member member) {
    return Post.builder()
        .title(req.getTitle())
        .postContent(req.getPostContent())
        .member(member)
        .postLike(0L)
        .build();
  }

  public static CreatePostResponse toCreatePostResponse(Post post) {
    return CreatePostResponse.builder()
        .title(post.getTitle())
        .content(post.getMember().getNickname())
        .build();
  }

  public static UpdatePostResponse toUpdatePostResponse(Post post) {
    return UpdatePostResponse.builder()
        .title(post.getTitle())
        .content(post.getMember().getNickname())
        .build();
  }
}
