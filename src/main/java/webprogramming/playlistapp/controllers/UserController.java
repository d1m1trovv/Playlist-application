package webprogramming.playlistapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.dtos.UserInfoDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.entities.User;
import webprogramming.playlistapp.services.PlaylistServiceImpl;
import webprogramming.playlistapp.services.UserDetailsImpl;
import webprogramming.playlistapp.services.UserServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    private final PlaylistServiceImpl playlistService;
    private final UserServiceImpl userService;

    public UserController(@Qualifier("playlistService") PlaylistServiceImpl playlistService,
                          @Qualifier("userService") UserServiceImpl userService){
        this.playlistService = playlistService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfoDto> getUserInfo(){
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserInfoDto userInfoDto = new UserInfoDto();

            userInfoDto.setEmail(((UserDetailsImpl) authentication.getPrincipal()).getEmail());
            userInfoDto.setUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername());


            return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/user/playlists")
    public ResponseEntity<List<PlaylistDto>> allPlaylists() {
        try {

            List<PlaylistDto> playlistsDto =
                    playlistService.findAll().stream()
                            .map(playlistService::convertPlaylistToDto)
                            .collect(Collectors.toList());

            if (playlistsDto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(playlistsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/playlists/{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable("id") long id) {
        try {
            Playlist playlist = playlistService.findPlaylistById(id).get();

            PlaylistDto playlistDto = playlistService.convertPlaylistToDto(playlist);

            //return playlistDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            //      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

            return new ResponseEntity<>(playlistDto, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/playlists/{id}")
    public UserDto subscribeToPlaylist(@PathVariable long id){
        try {
            Playlist playlist = playlistService.findPlaylistById(id).get();
            Set<User> userSet = playlist.getUserSet();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            return userService.findUserById(((UserDetailsImpl) auth.getPrincipal()).getId())
                    .map(user -> {
                        Set<Playlist> playlists = user.getUserPlaylists();
                        playlists.add(playlist);
                        user.setUserPlaylists(playlists);
                        userSet.add(user);
                        playlist.setUserSet(userSet);
                        playlistService.updatePlaylist(playlist);
                        return userService.convertUserToDto(userService.updateUser(user));
                    })
                    .orElseGet(() -> userService.convertUserToDto(userService.findUserById(((UserDetailsImpl) auth.getPrincipal()).getId()).get()));
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/user/subscriptions")
    public ResponseEntity<List<PlaylistDto>> getSubscriptions(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Set<Playlist> userPlaylists = userService.findUserById(((UserDetailsImpl) auth.getPrincipal())
                    .getId()).get().getUserPlaylists();

            List<PlaylistDto> playlistDtos = userPlaylists.stream()
                    .map(playlistService::convertPlaylistToDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(playlistDtos, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
