package com.example.week6.repository;

import com.example.week6.domain.Comment;
import com.example.week6.domain.CommentLike;
import com.example.week6.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByMemberAndComment(Member member, Comment comment);

    @Transactional void removeByMemberId(Long id);

    List<CommentLike> findByMemberId(Long id);
}
