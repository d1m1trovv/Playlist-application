package webprogramming.playlistapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.services.UserServiceImpl;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

}
