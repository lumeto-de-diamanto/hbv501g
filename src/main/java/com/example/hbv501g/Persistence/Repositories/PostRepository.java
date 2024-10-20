package com.example.hbv501g.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hbv501g.Persistence.Entities.Forum;
import com.example.hbv501g.Persistence.Entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);
    void delete(Post post);
    List<Post> findAll();
    List<Post> findByTitle(String title);
    Post findById(long post_id);
    List<Post> findPostByForum(Forum forum);
}
