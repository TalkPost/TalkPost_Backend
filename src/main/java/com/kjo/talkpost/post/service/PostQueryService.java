package com.kjo.talkpost.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kjo.talkpost.exception.GlobalException;
import com.kjo.talkpost.exception.errorCode.ErrorCode404;
import com.kjo.talkpost.post.entity.Post;
import com.kjo.talkpost.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

  private final PostRepository postRepository;

  public Post get(Long postId) {
    return postRepository
        .findById(postId)
        .orElseThrow(() -> new GlobalException(ErrorCode404.POST_NOT_FOUND));
  }

  public List<Post> getAll() {
    return validatePostsExist(postRepository.findAll());
  }

  public List<Post> getAllByMemberId(Long memberId) {
    return validatePostsExist(postRepository.findAllByMember_MemberId(memberId));
  }

  private List<Post> validatePostsExist(List<Post> posts) {
    if (posts == null || posts.isEmpty()) {
      throw new GlobalException(ErrorCode404.POST_NOT_FOUND);
    }
    return posts;
  }
}
