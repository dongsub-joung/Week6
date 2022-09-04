package com.example.week6.repository;

import com.example.week6.domain.Member;
import com.example.week6.domain.Post;
import com.example.week6.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByMemberAndPost(Member member, Post post);

    @Transactional
    void removeByMemberId(Long id);

    List<Post> findByMemberId(Long id);
}
