package com.example.hbv501g.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hbv501g.Persistence.Entities.Forum;
import com.example.hbv501g.Persistence.Entities.Post;
import com.example.hbv501g.Persistence.Repositories.PostRepository;
import com.example.hbv501g.Services.PostService;

@Service
public class PostServiceImplementation implements PostService {

    private PostRepository postRepository;
    @Autowired
    public PostServiceImplementation(PostRepository postRepository) {
       this.postRepository = postRepository;
    }


    @Override
    public Post findByTitle(String title) {
       return postRepository.findByTitle(title).get(0);
    }

    @Override
    public Post findById(long ID) {
        return postRepository.findById(ID);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public List<Post> getPostByForum(Forum forum) {
        return postRepository.findPostByForum(forum);
    }

    @Override
    public void likepost(long postID){
        Post post = postRepository.findById(postID);
        post.setLikes(post.getLikes() + 1); // Increment likes
        postRepository.save(post); // Save the updated post
    }

}
