package com.example.week6.domain;

import com.example.week6.controller.request.PostRequestDto;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
  private List<Comment> comments;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  private int likes;
  private String imageUrl;
  @Column(nullable = false)
  private int numberOfWatch;    // 상세 게시글 조회수


//  == 연관관계 메서드 ==//
  public void setMember(Member member) {
    this.member = member;
    member.getPosts().add(this);
  }

  public void update(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }


  public Post(String title, String content, Member member) {
    this.title = title;
    this.content = content;
    this.member = member;
  }

  public void addCount() {
    this.numberOfWatch += 1;
  }
}
