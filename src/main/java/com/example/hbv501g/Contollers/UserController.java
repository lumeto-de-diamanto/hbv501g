package com.example.hbv501g.Contollers;

import com.example.hbv501g.Persistence.Entities.User;
import com.example.hbv501g.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Gets the form to create new user
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(User user) {
        return "signup";
    }

    //Gets the data from the form and saves it to the database
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/signup";
        }
        User exists = userService.findByUsername(user.getUsername());
        if (exists == null) {
            userService.save(user);
        } else {
            System.out.println("This username is taken");
        }
        return "redirect:/";
    }

    //Gets the form to sign in
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user) {
        return "login";
    }

    //Gets the data from the form and checks if the user is in the database
    //Puts the user to the session
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }
        User exists = userService.login(user);
        if (exists != null) {
            session.setAttribute("LoggedInUser", exists);
            model.addAttribute("LoggedInUser", exists);
            return "LoggedInUser";
        }
        return "redirect:/";
    }

    //Gives you what user is loggedin
    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedinGET(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser != null) {
            model.addAttribute("LoggedInUser", sessionUser);
            //th
            return "redirect:/loggedin"; // Redirect to a logged-in user page
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "login"; // Return to login page with error
        }
    }

    //Logs the user from the session out.
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGET(HttpSession session) {
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if (sessionUser != null) {
            // Update user logged in status
            userService.setLoggedIn(sessionUser, false);
            // loka session
            session.invalidate();
            System.out.println("User logged out: " + sessionUser.getUsername());
        }
        return "redirect:/";
    }
/*
    @RequestMapping(value = "/forgotPassword", method = RequestMethod.PATCH)
    public String forgotPassword(String username, String password, Model model){
        User user = userService.findByUsername(username);
        if(user != null){
            userService.updateByPassword(username, password);
            model.addAttribute("user", user);
        }
        return "redirect:";
    }*/
}
