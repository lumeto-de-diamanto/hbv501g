package com.example.hbv501g.Contollers;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hbv501g.Persistence.Entities.Forum;
import com.example.hbv501g.Persistence.Entities.Post;
import com.example.hbv501g.Persistence.Entities.User;
import com.example.hbv501g.Services.ForumService;
import com.example.hbv501g.Services.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private ForumService forumService;
    private PostService postService;

    @Autowired
    public HomeController(ForumService forumService, PostService postService) {
        this.forumService = forumService;
        this.postService = postService;
    }

    @RequestMapping("/")
    public String homePage(Model model) {
        //call a method in a Service Class
        List<Forum> allForums = forumService.findAll();
        //Add some data to the Model
        model.addAttribute("forum", allForums);
        return "home";
    }

    @RequestMapping(value = "/addforum", method = RequestMethod.GET)
    public String addForumForm(Forum forum) {
        return "newForum";
    }

    @RequestMapping(value = "/addforum", method = RequestMethod.POST)
    public String addForum(Forum forum, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "newForum";
        }
        //sækja logged in user frá session
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        if (loggedInUser != null) {
            // setja user sem creator
            forum.setCreatedBy(loggedInUser);
            System.out.println(loggedInUser.getUserId());
            forumService.save(forum);
            //System.out.println("Forum creater: " + loggedInUser.getUsername());
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }


    @RequestMapping(value = "/forum/delete/{forumId}", method = RequestMethod.GET)
    public String deleteForum(@PathVariable("forumId") long id, HttpSession session) {
        Forum forumToDelete = forumService.findById(id);
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // bera saman id
        if (forumToDelete.getCreatedBy().getUserId() == loggedInUser.getUserId()) {
            List<Post> posts = postService.getPostByForum(forumToDelete);
            for (Post p : posts) {
                postService.delete(p);
            }
            forumService.delete(forumToDelete);
            System.out.println("forum deleted");
        } else {
            System.out.println("user can't delete forum");
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/forum/edit/{forumId}", method = RequestMethod.PATCH)
    public String editForum(@PathVariable("forumId") long id, @RequestParam(required = false) String name, 
                                                              @RequestParam(required = false) String description, 
                                                              @RequestParam(required = false) String category, HttpSession session) {
        Forum forumToEdit = forumService.findById(id);
        User loggedInUser = (User) session.getAttribute("LoggedInUser");

        // bera saman id
        if (forumToEdit.getCreatedBy().getUserId() == loggedInUser.getUserId()) {
            forumService.edit(forumToEdit, name, description, category);
            System.out.println("forum edited");
        } else {
            System.out.println("user can't edit forum");
        }
        return "redirect:/";
    }

    //@RequestMapping(value = "/forum/{forumId}", method = RequestMethod.GET)
    @GetMapping("/forum/{forumId}")
    public String intoForum(@PathVariable("forumId") long forumId, Model model, HttpSession session) {
        System.out.println("forumId = " + forumId);
        Forum forum = forumService.findById(forumId);
        List<Post> forumPosts = postService.getPostByForum(forum);
        Collections.sort(forumPosts, new PostComparator().reversed());
        Forum forumData = forum;
        session.setAttribute("ForumData", forumData);
        model.addAttribute("forum", forum);
        model.addAttribute("posts", forumPosts);
        model.addAttribute("newPosts", new Post());
        return "forum";
    }
}

class PostComparator implements java.util.Comparator<Post> {
    @Override
    public int compare(Post a, Post b) {
        return (a.getLikes() + b.getDislikes()) - (b.getLikes() + a.getDislikes());
    }
}
