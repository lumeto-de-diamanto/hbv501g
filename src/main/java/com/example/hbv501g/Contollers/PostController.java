package com.example.hbv501g.Contollers;

import com.example.hbv501g.Persistence.Entities.Post;
import com.example.hbv501g.Services.ForumService;
import com.example.hbv501g.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {

    private PostService postService;
    private ForumService forumService;

    @Autowired
    public PostController(PostService postService, ForumService forumService){
        this.postService = postService;
        this.forumService = forumService;
    }

    @RequestMapping(value = "/forum/{forumId}/addpost", method = RequestMethod.GET)
    public String addPostForm(Post post){
        return "newPost";
    }

    @RequestMapping(value = "/forum/{forumId}/addpost", method = RequestMethod.POST)
    public String addPost(@PathVariable("forumId") long forumId, Post post, BindingResult result, Model model){
        if(result.hasErrors()){
            return "newPost";
        }

        postService.save(post);
        return "redirect:/";
    }

    @RequestMapping(value = "/posts/delete/{postId}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("postId") long id, Model model){
        Post postToDelete = postService.findById(id);
        postService.delete(postToDelete);
        return "redirect:/";
    }
}
