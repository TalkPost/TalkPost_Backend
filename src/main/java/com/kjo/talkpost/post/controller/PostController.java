package com.kjo.talkpost.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.kjo.talkpost.common.GlobalResponse;
import com.kjo.talkpost.converter.PostConverter;
import com.kjo.talkpost.exception.errorCode.ErrorCode200;
import com.kjo.talkpost.post.dto.PostRequestDto.*;
import com.kjo.talkpost.post.dto.PostResponseDto.*;
import com.kjo.talkpost.post.service.PostCommandService;
import com.kjo.talkpost.post.service.PostQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post API", description = "Post API")
@RequestMapping("/api/v1/posts")
public class PostController {

  private final PostCommandService postCommandService;
  private final PostQueryService postQueryService;

  @Operation(summary = "글 등록 API", description = "글 등록 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping
  public GlobalResponse<CreatePostResponse> regPost(@RequestBody CreatePostRequest req) {
    return GlobalResponse.onSuccess(
        ErrorCode200.CREATED, PostConverter.toCreatePostResponse(postCommandService.reg(req)));
  }

  @Operation(summary = "글 목록 조회 API", description = "글 목록 조회 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @GetMapping
  public GlobalResponse<List<GetPostResponse>> getAllPost() {
    return GlobalResponse.onSuccess(PostConverter.toGetAllPostResponse(postQueryService.getAll()));
  }

  @Operation(summary = "글 상세 조회 API", description = "글 상세 조회 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @GetMapping("{postId}")
  public GlobalResponse<GetPostResponse> getPost(@PathVariable Long postId) {
    return GlobalResponse.onSuccess(PostConverter.toGetPostResponse(postQueryService.get(postId)));
  }

  @Operation(summary = "회원별 글 목록 조회 API", description = "회원별 글 목록 조회 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @GetMapping("/member")
  public GlobalResponse<List<GetPostResponse>> getAllPostByMember() {
    return GlobalResponse.onSuccess(
        PostConverter.toGetAllPostResponse(postQueryService.getAllByMember()));
  }

  @Operation(summary = "글 수정 API", description = "글 수정 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PutMapping("{postId}")
  public GlobalResponse<UpdatePostResponse> updatePost(
      @PathVariable Long postId, @RequestBody UpdatePostRequest req) {
    return GlobalResponse.onSuccess(
        ErrorCode200.UPDATED,
        PostConverter.toUpdatePostResponse(postCommandService.update(postId, req)));
  }

  @Operation(summary = "글 삭제 API", description = "글 삭제 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "성공적으로 삭제되었습니다"),
  })
  @DeleteMapping("{postId}")
  public GlobalResponse<Void> deletePost(@PathVariable Long postId) {
    postCommandService.delete(postId);
    return GlobalResponse.onSuccess(ErrorCode200.DELETED, null);
  }
}
