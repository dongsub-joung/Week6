package com.example.week6.domain;

import com.example.week6.controller.request.CommentRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(of = {"member","post","content"})
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "post_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @Column(nullable = false)
  private String content;

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
  List<CommentLike> likes = new ArrayList<>();

  //== 연관관계 메서드 ==//
  public void setMember(Member member) {
    this.member = member;
    member.getComments().add(this);
  }


  public Comment(Member member, String content) {
    this.member = member;
    this.content = content;
  }

  public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }
}
