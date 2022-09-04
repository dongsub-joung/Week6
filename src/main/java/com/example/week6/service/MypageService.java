package com.example.week6.service;

import com.example.week6.controller.response.PostResponseDto;
import com.example.week6.controller.response.ResponseDto;
import com.example.week6.domain.*;
import com.example.week6.jwt.TokenProvider;
import com.example.week6.repository.CommentLikeRepository;
import com.example.week6.repository.CommentRepository;
import com.example.week6.repository.PostLikeRepository;
import com.example.week6.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final TokenProvider tokenProvider;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 내가 누른 좋아요 게시글 들 페이지들 가져오기
    // 내가 작성한 게시글 가져오기
    // 내가 작성한 댓글 가져오기

    public ResponseDto<?> getAllMyActs(HttpServletRequest request) {

        // 회원 정보 가져오기
        Member member = validateMember(request);

        List<Post> postList = postRepository.findAllByMemberId(member.getId());
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        // 좋아요 한 게시물 및 댓글
        List<Post>  =postLikeRepository.findByMemberId(member.getId());

    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
