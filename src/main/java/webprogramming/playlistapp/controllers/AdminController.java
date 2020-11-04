package webprogramming.playlistapp.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.entities.User;
import webprogramming.playlistapp.services.PlaylistServiceImpl;
import webprogramming.playlistapp.services.SongServiceImpl;
import webprogramming.playlistapp.services.UserServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AdminController {

    private final
    UserServiceImpl userService;

    private final
    PlaylistServiceImpl playlistService;

    private final SongServiceImpl songService;

    public AdminController(@Qualifier("userService") UserServiceImpl userService,
                           @Qualifier("playlistService") PlaylistServiceImpl playlistService,
                           @Qualifier("songService") SongServiceImpl songService) {
        this.userService = userService;
        this.playlistService = playlistService;
        this.songService = songService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> Register(@Valid @RequestBody UserDto userDto){
        try {

            userService.createUser(userDto);

            return new ResponseEntity<>("User registered!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/admin/playlists")
    public ResponseEntity<List<Playlist>> allPlaylists() {
        try {

            List<Playlist> playlists = new ArrayList<>(playlistService.findAll());

            if (playlists.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(playlists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/playlists/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable("id") long id) {
        Optional<Playlist> playlist = playlistService.findPlaylistById(id);

        return playlist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/admin/playlists/{id}")
    public Playlist updatePlaylist(@PathVariable("id") long id, @RequestBody Playlist playlistEnt) {
        return playlistService.findPlaylistById(id)
                .map(playlist -> {
                    playlist.setTitle(playlistEnt.getTitle());
                    playlist.setAuthor(playlistEnt.getAuthor());
                    playlist.setGenre(playlistEnt.getGenre());
                    playlist.setSubFee(playlistEnt.getSubFee());
                    return playlistService.updatePlaylist(playlist);
                })
                .orElseGet(() -> {
                    playlistEnt.setId(id);
                    return playlistService.updatePlaylist(playlistEnt);
                });
    }

    @DeleteMapping("/admin/playlists/{id}")
    public ResponseEntity<HttpStatus> deletePlaylist(@PathVariable("id") long id) {
        try {
            playlistService.deletePlaylist(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/admin/addPlaylist")
    public ResponseEntity<?> addPlaylist(@Valid @RequestBody PlaylistDto playlistDto){
        try {

            playlistService.createPlaylist(playlistDto);

            return new ResponseEntity<>("Playlist created!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed", HttpStatus.EXPECTATION_FAILED);
        }
    }


    @GetMapping(value = "/admin/users")
    public ResponseEntity<List<User>> allUsers() {
        try {

            List<User> users = new ArrayList<>(userService.findAll());

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> user = userService.findUserById(id);

        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/admin/users/{id}")
    public User updateUser(@PathVariable("id") long id, @RequestBody User userEnt) {
        return userService.findUserById(id)
                .map(user -> {
                    user.setEmail(userEnt.getEmail());
                    user.setUsername(userEnt.getUsername());
                    return userService.updateUser(user);
                })
                .orElseGet(() -> {
                    userEnt.setId(id);
                    return userService.updateUser(userEnt);
                });
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/admin/playlists/{id}/addSong")
    public ResponseEntity<?> addSong(@PathVariable("id") long id,
                                        @Valid @RequestBody SongDto songDto){
        try {

            playlistService.addSong(id, songDto);

            return new ResponseEntity<>("Playlist created!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/admin/songs")
    public ResponseEntity<List<Song>> allSongs() {
        try {

            List<Song> songs = new ArrayList<>(songService.findAllSongs());

            if (songs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(songs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/playlists/playlistSongs")
    public ResponseEntity<List<Song>> getPlaylistSongs(@RequestParam(name = "id") Long id){
        try {

            List<Song> songs = new ArrayList<>(playlistService.findAllPlaylistSongs(id));

            if (songs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(songs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



