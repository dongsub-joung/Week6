//package com.example.week6.domain;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//class MemberTest {
//
//    @Autowired EntityManager em;
//
//    @Test
//    public void postMemberConnection() {
//        //given
//        Member member1 = new Member("memberA", "password");
//        Member member2 = new Member("memberB", "password");
//        em.persist(member1);
//        em.persist(member2);
//
//        Post post1 = new Post("post1", "content", member1);
//        Post post2 = new Post("post2", "content", member2);
//        Post post3 = new Post("post3", "content", member1);
//        Post post4 = new Post("post4", "content", member1);
//        em.persist(post1);
//        em.persist(post2);
//        em.persist(post3);
//        em.persist(post4);
//
//        em.flush();
//        em.clear();
//        //when
//        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
//
//        //then
//        for (Member member : members) {
//            System.out.println("member = " + member);
//            List<Post> posts = member.getPosts();
//            for (Post post : posts) {
//                System.out.println("post = " + post);
//            }
//        }
//    }
//
//    @Test
//    public void postCommentConnection(){
//        //given
//        Member member1 = new Member("memberA", "password");
//        Member member2 = new Member("memberB", "password");
//        em.persist(member1);
//        em.persist(member2);
//
//        Post post1 = new Post("post1", "content", member1);
//        Post post2 = new Post("post2", "content", member2);
//        em.persist(post1);
//        em.persist(post2);
//
//        Comment comment1 = new Comment(member1, post1, "comment1");
//        Comment comment2 = new Comment(member1, post1, "comment2");
//        Comment comment3 = new Comment(member1, post2, "comment3");
//        Comment comment4 = new Comment(member2, post1, "comment4");
//        Comment comment5 = new Comment(member2, post2, "comment5");
//        em.persist(comment1);
//        em.persist(comment2);
//        em.persist(comment3);
//        em.persist(comment4);
//        em.persist(comment5);
//
//        em.flush();
//        em.clear();
//        //when
//        List<Post> posts = em.createQuery("select p from Post p", Post.class).getResultList();
//
//        //then
//        for (Post post : posts) {
//            System.out.println("post = " + post);
//            List<Comment> comments = post.getComments();
//            for (Comment comment : comments) {
//                System.out.println("comment = " + comment);
//            }
//        }
//
//    }
//
//
//
//}