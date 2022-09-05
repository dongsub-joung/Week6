package com.example.week6.controller;

import com.example.week6.controller.request.PostRequestDto;
import com.example.week6.controller.response.ResponseDto;
import com.example.week6.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  // 게시글 작성
  @RequestMapping(value = "/api/post", method = RequestMethod.POST)
  public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,
                                   HttpServletRequest request) {
    return postService.createPost(requestDto, request);
  }

  // 상세 게시글 조회
  @RequestMapping(value = "/api/post/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getPost(@PathVariable Long id) {
    return postService.getPost(id);
  }

  //전체 게시글 조회
  @RequestMapping(value = "/api/list", method = RequestMethod.GET)
  public ResponseDto<?> getAllPosts() {
    return postService.getAllPost();
  }

  //게시글 수정
  @RequestMapping(value = "/api/post/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
      HttpServletRequest request) {
    return postService.updatePost(id, postRequestDto, request);
  }

  // 게시글 삭제
  @RequestMapping(value = "/api/post/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deletePost(@PathVariable Long id,
      HttpServletRequest request) {
    return postService.deletePost(id, request);
  }

}
