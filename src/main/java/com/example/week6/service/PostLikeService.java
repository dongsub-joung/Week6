package com.example.week6.service;

import com.example.week6.controller.response.ResponseDto;
import com.example.week6.domain.Member;
import com.example.week6.domain.Post;
import com.example.week6.domain.PostLike;
import com.example.week6.jwt.TokenProvider;
import com.example.week6.repository.PostLikeRepository;
import com.example.week6.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> likePost(Long id, HttpServletRequest request) {

        Post post = isPresentPost(id);
        Member member = validateMember(request);

        // 게시글 검증
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id입니다.");
        }

        // 좋아요 중복 검증
        if (isNotAlreadyLike(member, post)) {
            postLikeRepository.save(new PostLike(post, member));
            return ResponseDto.success("좋아요가 성공적으로 반영되었습니다.");
        }
        return ResponseDto.fail("ALREADY_LIKE","이미 좋아요를 누르셨습니다.");
    }

    public ResponseDto<?> dislikePost(Long id, HttpServletRequest request) {
        Post post = isPresentPost(id);
        Member member = validateMember(request);

        // 게시글 검증
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id입니다.");
        }

        if (!isNotAlreadyLike(member, post)) {
            postLikeRepository.removeByMemberId(member.getId());
            return ResponseDto.success("좋아요 취소.");
        }
        return ResponseDto.fail("DIDNT_LIKE", "좋아요를 누르시지 않으셨습니다. ");
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    private boolean isNotAlreadyLike(Member member, Post post) {
        return postLikeRepository.findByMemberAndPost(member, post).isEmpty();
    }

}
