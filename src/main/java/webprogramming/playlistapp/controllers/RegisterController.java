package webprogramming.playlistapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.services.UserServiceImpl;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user" , new UserDto());
        mav.setViewName("register");
        return mav;
    }

    @PostMapping(value = "/register")
    public ModelAndView registerUser(@RequestBody @Valid @ModelAttribute("user") UserDto userDto, BindingResult bd){
        ModelAndView mav = new ModelAndView();

        if(userService.checkIfUserExists(userDto.getEmail())){
            bd.rejectValue("email", "error.user", "User with this email already exists");
            mav.addObject("emailExists", "User with this email already exists");
            mav.setViewName("register");
        }

        if(!userService.passwordMatches(userDto) || bd.hasErrors()){
            mav.addObject("passwordMatch", "Password doesn't match");
            mav.setViewName("register");
        }
        else{
            userService.createUser(userDto);
            mav.addObject("registrationSuccess", "You have successfully registered!");
            mav.addObject("user", new UserDto());
            mav.setViewName("register");
        }
        return mav;
    }
}
