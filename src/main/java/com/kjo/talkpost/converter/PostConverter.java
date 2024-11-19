package com.kjo.talkpost.converter;

import java.util.List;
import java.util.stream.Collectors;

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

  public static List<GetPostResponse> toGetPostListResponse(List<Post> posts) {
    return posts.stream()
        .map(
            post ->
                GetPostResponse.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .nickname(post.getMember().getNickname())
                    .build())
        .collect(Collectors.toList());
  }

  public static GetPostResponse toGetPostResponse(Post post) {
    return GetPostResponse.builder()
        .postId(post.getPostId())
        .title(post.getTitle())
        .content(post.getPostContent())
        .nickname(post.getMember().getNickname())
        .build();
  }
}
