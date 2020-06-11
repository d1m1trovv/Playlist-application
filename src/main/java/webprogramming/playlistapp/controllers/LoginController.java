package webprogramming.playlistapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.services.UserServiceImpl;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }
}
