package com.kjo.talkpost.post.controller;

import org.springframework.web.bind.annotation.*;

import com.kjo.talkpost.common.GlobalResponse;
import com.kjo.talkpost.converter.PostConverter;
import com.kjo.talkpost.exception.errorCode.ErrorCode200;
import com.kjo.talkpost.post.dto.PostRequestDto.*;
import com.kjo.talkpost.post.dto.PostResponseDto.*;
import com.kjo.talkpost.post.service.PostCommandService;

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

  @Operation(summary = "글 등록 API", description = "글 등록 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PostMapping
  public GlobalResponse<CreatePostResponse> create(@RequestBody CreatePostRequest req) {
    return GlobalResponse.onSuccess(
        ErrorCode200.CREATED, PostConverter.toCreatePostResponse(postCommandService.create(req)));
  }

  @Operation(summary = "글 수정 API", description = "글 수정 API 입니다.")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "성공"),
  })
  @PutMapping("{postId}")
  public GlobalResponse<UpdatePostResponse> update(
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
  public GlobalResponse<Void> delete(@PathVariable Long postId) {
    postCommandService.delete(postId);
    return GlobalResponse.onSuccess(ErrorCode200.DELETED, null);
  }
}
