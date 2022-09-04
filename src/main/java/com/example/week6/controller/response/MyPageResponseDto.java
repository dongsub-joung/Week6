package com.example.week6.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {

    private List<PostResponseDto> postList;
    private List<CommentResponseDto> commentList;
    private String title;
    private String content;
    private String author;
    private LocalDateTime modifiedAt;
}
