package webprogramming.playlistapp.controllers;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.entities.User;
import webprogramming.playlistapp.services.UserServiceImpl;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private @Qualifier("userService") UserServiceImpl userService;


    @GetMapping(value = "/api/all")
    public List<User> all(){
        return userService.findAll();
    }

    @GetMapping(value = "/api/homepage")
    public String home(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ADMIN"));
        return isAdmin ? "redirect:/admin/homepage" : "redirect:/user/homepage";
    }

    @GetMapping(value = "/access-denied")
    public String accessDenied(){
        return "access-denied";
    }
}


