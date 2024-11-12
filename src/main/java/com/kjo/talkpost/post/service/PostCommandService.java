package com.kjo.talkpost.post.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kjo.talkpost.converter.PostConverter;
import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode400;
import com.kjo.talkpost.exception.errorCode.ErrorCode404;
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

  public Post create(CreatePostRequest req) {
    validatePost(req.getTitle(), req.getPostContent());
    return postRepository.save(PostConverter.toPost(req, MemberDetailsService.getCurrentMember()));
  }

  public Post update(Long postId, UpdatePostRequest req) {
    validatePost(req.getTitle(), req.getPostContent());

    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new GlobalException(ErrorCode404.POST_NOT_FOUND));

    post.update(req);

    return post;
  }

  public void delete(Long postId) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new GlobalException(ErrorCode404.POST_NOT_FOUND));

    postRepository.delete(post);
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
