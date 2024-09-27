package com.example.hbv501g.Contollers;
import com.example.hbv501g.Persistence.Entities.Forum;
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
import java.util.List;

@Controller
public class HomeController {

    private ForumService forumService;
    private PostService postService;

    @Autowired
    public HomeController(ForumService forumService, PostService postService){
        this.forumService = forumService;
        this.postService = postService;
    }

    @RequestMapping("/")
    public String homePage(Model model){
        //call a method in a Service Class
        List<Forum> allForums = forumService.findAll();
        //Add some data to the Model
        model.addAttribute("forum", allForums);
        return "home";
    }

    @RequestMapping(value = "/addforum", method = RequestMethod.GET)
    public String addForumForm(Forum forum){return "newForum";}

    @RequestMapping(value = "/addforum", method = RequestMethod.POST)
    public String addForum(Forum forum, BindingResult result, Model model){
        if (result.hasErrors()){
            return "newForum";
        }
        forumService.save(forum);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{forumId}", method = RequestMethod.GET)
    public String deleteForum(@PathVariable("forumId") long id, Model model){
        Forum forumToDelete = forumService.findById(id);
        forumService.delete(forumToDelete);
        return "redirect:/";
    }

    @RequestMapping(value = "/{forumId}", method = RequestMethod.GET)
    public String intoForum(long forumId, Model model){
        Forum forum = forumService.findById(forumId);
        List<Post> posts = postService.findAllByForumId(forumId);

        model.addAttribute("forum", forum);
        model.addAttribute("posts", posts);
        //model.addAttribute("newPosts", new Post());
        return "forum";}
}
