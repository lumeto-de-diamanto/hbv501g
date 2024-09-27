package com.example.hbv501g.Persistence.Repositories;

import com.example.hbv501g.Persistence.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post save(Post post);
    void delete(Post post);

    List<Post> findAll();
    List<Post> findByTitle(String title);
    Post findById(long post_id);
    List<Post> findAllByForumId(long forumId);

}
