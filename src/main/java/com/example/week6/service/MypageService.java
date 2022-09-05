package com.example.week6.service;

import com.example.week6.controller.response.*;
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
import java.util.ArrayList;
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

        // 회원이 작성한 게시글 리스트 가져오기
        List<Post> postList = postRepository.findAllByMemberId(member.getId());
        List<AllPostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .imageUrl(post.getImageUrl())
                            .createdTime(post.getCreatedAt())
                            .username(post.getMember().getUsername())
                            .watch(post.getNumberOfWatch())
                            .likes(post.getLikes().size())
                            .build()
            );
        }
        // 회원이 작성한 댓글 리스트 가져오기
        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .author(comment.getMember().getUsername())
                            .content(comment.getContent())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );

        }
        // 좋아요 한 게시물  가져오기
        List<PostLike> postLikeList = postLikeRepository.findByMemberId(member.getId());
        List<AllPostResponseDto> postLikeResponseDtoList = new ArrayList<>();
        for (PostLike postLike : postLikeList) {
            System.out.println("post.getMember = " + postLike.getMember().getId());
            System.out.println("post.getPost().getId() = " + postLike.getPost().getId());
            Post post = postRepository.findById(postLike.getPost().getId()).get();
            postLikeResponseDtoList.add(
                    AllPostResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .imageUrl(post.getImageUrl())
                            .createdTime(post.getCreatedAt())
                            .username(post.getMember().getUsername())
                            .watch(post.getNumberOfWatch())
                            .likes(post.getLikes().size())
                            .build()
            );
        }

        //좋아요 한 댓글 가져오기
        List<CommentLike> commentLikeList = commentLikeRepository.findByMemberId(member.getId());
        List<CommentResponseDto> commentLikeResponseDtoList = new ArrayList<>();
        for (CommentLike commentLike : commentLikeList) {
            System.out.println("commentLike = " + commentLike.getMember().getId());
            System.out.println("commentLike.getComment().getId( = " + commentLike.getComment().getId());
            Comment comment = commentRepository.findById(commentLike.getComment().getId()).get();
            commentLikeResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getUsername())
                            .content(comment.getContent())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(
                MyPageResponseDto.builder()
                        .postList(postResponseDtoList)
                        .commentList(commentResponseDtoList)
                        .postLikeList(postLikeResponseDtoList)
                        .commentLikeList(commentLikeResponseDtoList)
                        .build()
        );

    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
