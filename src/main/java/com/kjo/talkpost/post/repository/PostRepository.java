package com.kjo.talkpost.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjo.talkpost.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByMember_MemberId(Long memberId);
}
