package com.example.hbv501g.Services;

import java.util.List;

import com.example.hbv501g.Persistence.Entities.Forum;
import com.example.hbv501g.Persistence.Entities.Post;

public interface PostService {
    Post findByTitle(String title);
    Post findById(long ID);
    List<Post> findAll();
    Post save(Post post);
    void delete(Post post);
    List<Post> getPostByForum(Forum forum);
    void likepost(long postID);
}
