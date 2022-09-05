package com.example.week6.controller;

import com.example.week6.controller.request.CommentRequestDto;
import com.example.week6.controller.response.ResponseDto;
import com.example.week6.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

  private final CommentService commentService;

  @RequestMapping(value = "/api/comment", method = RequestMethod.POST)
  public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                      HttpServletRequest request) {
    return commentService.createComment(requestDto, request);
  }
  @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getAllComments(@PathVariable Long id) {
    return commentService.getAllCommentsByPost(id);
  }

  @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
      HttpServletRequest request) {
    return commentService.updateComment(id, requestDto, request);
  }

  @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deleteComment(@PathVariable Long id,
      HttpServletRequest request) {
    return commentService.deleteComment(id, request);
  }
}
