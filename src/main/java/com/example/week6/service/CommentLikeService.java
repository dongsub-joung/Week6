package com.example.week6.service;


import com.example.week6.controller.response.ResponseDto;
import com.example.week6.domain.*;
import com.example.week6.jwt.TokenProvider;
import com.example.week6.repository.CommentLikeRepository;
import com.example.week6.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final TokenProvider tokenProvider;

    public ResponseDto<?> likeComment(Long id, HttpServletRequest request) {
        Comment comment = isPresentComment(id);
        Member member = validateMember(request);

        // 토큰 검증
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        // 회원 검증

        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        // 댓글 검증
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id입니다.");
        }

        // 좋아요 중복 검증
        if (isNotAlreadyLike(member, comment)) {
            commentLikeRepository.save(new CommentLike(comment, member));
            return ResponseDto.success("좋아요가 성공적으로 반영되었습니다.");
        }
        return ResponseDto.fail("ALREADY_LIKE", "이미 좋아요를 누르셨습니다.");
    }




    public ResponseDto<?> dislikeComment(Long id, HttpServletRequest request) {
        Comment comment = isPresentComment(id);
        Member member = validateMember(request);

        // 댓글 검증
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id입니다.");
        }

        // 좋아요 취소
        if (!isNotAlreadyLike(member, comment)) {
            commentLikeRepository.removeByMemberId(member.getId());
            return ResponseDto.success("좋아요 취소.");
        }
        return ResponseDto.fail("DIDNT_LIKE", "좋아요를 누르시지 않으셨습니다. ");
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    private boolean isNotAlreadyLike(Member member, Comment comment) {
        return commentLikeRepository.findByMemberAndComment(member, comment).isEmpty();
    }

}
