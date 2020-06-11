package webprogramming.playlistapp.controllers;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.entities.User;
import webprogramming.playlistapp.services.PlaylistServiceImpl;
import webprogramming.playlistapp.services.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private @Qualifier("userService")
    UserServiceImpl userService;

    @Autowired
    private @Qualifier("playlistService")
    PlaylistServiceImpl playlistService;

    @GetMapping(value = "/admin/homepage")
    public ModelAndView adminHomePage(){
        ModelAndView mav = new ModelAndView("/adminViews/homepage");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        mav.addObject("loggedMessage", "Logged in as: " + user.getUsername());
        mav.addObject("adminRole", "Privileges: " + user.getRoles());
        return mav;
    }

    @GetMapping(value = "/admin/playlists")
    public ModelAndView playlists(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("playlists", playlistService.findAll());
        mav.setViewName("/adminViews/playlists");
        return mav;
    }

    @GetMapping(value = "/admin/addPlaylist")
    public ModelAndView addPlaylist(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("playlist", new PlaylistDto());
        mav.setViewName("/adminViews/addPlaylist");
        return mav;
    }

    @PostMapping(value = "/admin/addPlaylist")
    public ModelAndView createPlaylist(@Valid @ModelAttribute("playlist")PlaylistDto playlistDto,
                                       BindingResult bd){
        ModelAndView mav = new ModelAndView();

        if(playlistService.checkIfPlaylistExists(playlistDto.getTitle())){
            bd.rejectValue("title", "Playlist with this title already exists");
            mav.addObject("playlistExists", "Playlist with this title already exists");
        }
        else{
            playlistService.createPlaylist(playlistDto);
            mav.addObject("successful", "You successfully created new playlist");
            mav.addObject("playlist", new PlaylistDto());
        }
        mav.setViewName("adminViews/addPlaylist");
        return mav;
    }

    @GetMapping(value = "/admin/addSong/{id}")
    public ModelAndView showAddSongToPlaylist(@PathVariable ("id") int id){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminViews/addSong");
        mav.addObject("song", new SongDto());
        return mav;
    }

    @PostMapping(value = "/admin/addSong/{id}")
    public ModelAndView addSongToPlaylist(@PathVariable("id") int id, @Valid @ModelAttribute("song") SongDto songDto,
                                          BindingResult bd){
        RedirectView redirectView = new RedirectView("/admin/addSong/{id}");
        ModelAndView mav = new ModelAndView(redirectView);
        if(playlistService.checkIfSongExists(songDto.getName(), playlistService.findById(id).getTitle())){
            mav.addObject("playlistExists", "Song with the same name already exists");
            bd.rejectValue("name", "Song with this name exists");
        }
        else{
            playlistService.addSong(id, songDto);
            mav.addObject("success", "Song is successfully added");
            mav.addObject("song", new SongDto());
        }

        return mav;
    }

    @GetMapping(value = "/admin/songs/{id}")
    public ModelAndView showSongs(@PathVariable ("id") int id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("songs", playlistService.findAllPlaylistSongs(playlistService.findById(id).getTitle()));
        mav.setViewName("adminViews/songs");
        return mav;
    }

}



