package com.example.week6.repository;

import com.example.week6.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByOrderByModifiedAtDesc();

  List<Post> findAllByMemberId(Long id);

//  Post findByPostId(Long id);
}
