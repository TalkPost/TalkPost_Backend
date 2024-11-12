package com.kjo.talkpost.post.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.springframework.data.annotation.LastModifiedDate;

import com.kjo.talkpost.common.BaseEntity;
import com.kjo.talkpost.member.entity.Member;
import com.kjo.talkpost.post.dto.PostRequestDto.*;

import lombok.*;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id", nullable = false)
  private Long postId;

  @Column(name = "title", length = 50, nullable = false)
  private String title;

  @Column(name = "post_content", columnDefinition = "TEXT", nullable = false)
  private String postContent;

  @Column(name = "post_like", nullable = false)
  private Long postLike;

  @LastModifiedDate private LocalDateTime updateAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  public void update(UpdatePostRequest req) {
    this.title = req.getTitle();
    this.postContent = req.getPostContent();
  }
}
