package webprogramming.playlistapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import webprogramming.playlistapp.entities.User;
import webprogramming.playlistapp.services.UserServiceImpl;

@Controller
public class UserController {

    @Autowired
    private @Qualifier("userService") UserServiceImpl userService;

    @GetMapping(value = "/user/homepage")
    public ModelAndView userHomePage(){
        ModelAndView mav = new ModelAndView("/userViews/homepage");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        mav.addObject("loggedMessage", "Logged in as: " + user.getUsername());
        mav.addObject("userRole", "Privileges: " + user.getRoles());
        return mav;
    }


}
