package com.example.week6.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllPostResponseDto {

    //필요한 목록 --> 제목, 사진Url, 업로드시간, 작성자, 조회수

    private Long postId;
    private String title;
    private String imageUrl;
    private LocalDateTime createdTime;
    private String username;
    private int watch;
    private int likes;


}
