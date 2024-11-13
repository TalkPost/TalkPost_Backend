package com.kjo.talkpost.post.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kjo.talkpost.converter.PostConverter;
import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode400;
import com.kjo.talkpost.jwt.MemberDetailsService;
import com.kjo.talkpost.post.dto.PostRequestDto.*;
import com.kjo.talkpost.post.entity.Post;
import com.kjo.talkpost.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandService {

  private final PostRepository postRepository;
  private final PostQueryService postQueryService;

  public Post reg(CreatePostRequest req) {
    validatePost(req.getTitle(), req.getPostContent());

    return postRepository.save(PostConverter.toPost(req, MemberDetailsService.getCurrentMember()));
  }

  public Post update(Long postId, UpdatePostRequest req) {
    validatePost(req.getTitle(), req.getPostContent());
    Post post = postQueryService.get(postId);
    post.update(req);

    return post;
  }

  public void delete(Long postId) {
    postRepository.delete(postQueryService.get(postId));
  }

  private void validatePost(String title, String content) {
    if (title == null || title.isEmpty()) {
      throw new GlobalException(ErrorCode400.TITLE_NOT_VALID);
    } else if (title.length() > 50) {
      throw new GlobalException(ErrorCode400.TITLE_NOT_VALID);
    } else if (content == null || content.isEmpty()) {
      throw new GlobalException(ErrorCode400.CONTENT_NOT_VALID);
    }
  }
}
