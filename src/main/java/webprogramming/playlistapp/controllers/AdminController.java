package webprogramming.playlistapp.controllers;

import org.modelmapper.ModelMapper;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AdminController {

    private final
    UserServiceImpl userService;

    private final
    PlaylistServiceImpl playlistService;

    private final
    SongServiceImpl songService;

    private final ModelMapper modelMapper;

    public AdminController(@Qualifier("userService") UserServiceImpl userService,
                           @Qualifier("playlistService") PlaylistServiceImpl playlistService,
                           @Qualifier("songService") SongServiceImpl songService,
                           @Qualifier("modelMapper") ModelMapper modelMapper) {
        this.userService = userService;
        this.playlistService = playlistService;
        this.songService = songService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> Register(@Valid @RequestBody UserDto userDto) {
        try {

            userService.createUser(userDto);

            return new ResponseEntity<>("User registered!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(value = "/admin/playlists")
    public ResponseEntity<List<PlaylistDto>> allPlaylists() {
        try {

            List<PlaylistDto> playlistsDto =
                    playlistService.findAll().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            if (playlistsDto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(playlistsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/playlists/{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable("id") long id) {
        try {
            Playlist playlist = playlistService.findPlaylistById(id).get();

            PlaylistDto playlistDto = convertToDto(playlist);

            //return playlistDto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            //      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

            return new ResponseEntity<>(playlistDto, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/playlists/{id}")
    public PlaylistDto updatePlaylist(@PathVariable("id") long id, @RequestBody PlaylistDto playlistDto) {
        try {
            Playlist tempPl = convertToEntity(playlistDto);

            return playlistService.findPlaylistById(id)
                    .map(playlist -> {
                        playlist.setTitle(playlistDto.getTitle());
                        playlist.setAuthor(playlistDto.getAuthor());
                        playlist.setGenre(playlistDto.getGenre());
                        playlist.setSubFee(playlistDto.getSubFee());
                        playlistService.updatePlaylist(playlist);
                        return convertToDto(playlist);
                    })
                    .orElseGet(() -> {
                        playlistDto.setId(id);
                        return convertToDto(playlistService.updatePlaylist(tempPl));
                    });
        }catch (Exception e){
            return null;
        }
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
    public ResponseEntity<?> addPlaylist(@Valid @RequestBody PlaylistDto playlistDto) {
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

    @PostMapping("/admin/addSong")
    public ResponseEntity<?> addSong(@Valid @RequestBody SongDto songDto) {
        try {

            songService.createSong(songDto);

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

    @GetMapping("/admin/playlists/{id}/")
    public ResponseEntity<List<Song>> getPlaylistSongs(@PathVariable long id) {
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

    @GetMapping("/admin/addSongsToPlaylist/{id}")
    public ResponseEntity<List<Song>> getSongsToAdd(@PathVariable long id) {
        try {

            List<Song> songs = songService.findAllSongs();
            List<Song> songsToRemove = new ArrayList<>();

            for (Song song : songs) {
                for (Playlist playlist : song.getPlaylistSet()) {
                    if (playlist.getId() == id) {
                        songsToRemove.add(song);
                    }
                }
            }

            songs.removeAll(songsToRemove);

            if (songs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(songs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/addSongsToPlaylist/{id}")
    public PlaylistDto addSongToPlaylist(@PathVariable long id,
                                      @RequestBody SongDto songDto) {
        try {
            Song song = convertSongToEntity(songDto);
            Set<Playlist> playlistSet = song.getPlaylistSet();

            return playlistService.findPlaylistById(id)
                    .map(playlist -> {
                        Set<Song> songs = playlist.getSongs();
                        songs.add(song);
                        playlist.setSongs(songs);
                        playlistSet.add(playlist);
                        song.setPlaylistSet(playlistSet);
                        songService.updateSong(song);
                        return convertToDto(playlistService.updatePlaylist(playlist));
                    })
                    .orElseGet(() -> {
                        playlistService.findPlaylistById(id).get().setId(id);
                        return convertToDto(playlistService.updatePlaylist(playlistService.findPlaylistById(id).get()));
                    });
        }catch (Exception e){
            return null;
        }
    }

    private PlaylistDto convertToDto(Playlist playlist) {
        PlaylistDto playlistDto = modelMapper.map(playlist, PlaylistDto.class);
        playlistDto.setId(playlist.getId());
        playlistDto.setAuthor(playlist.getAuthor());
        playlistDto.setGenre(playlist.getGenre());
        playlistDto.setSubFee(playlist.getSubFee());
        playlistDto.setTitle(playlist.getTitle());
        return playlistDto;
    }

    private Playlist convertToEntity(PlaylistDto playlistDto) throws ParseException {
        Playlist playlist = modelMapper.map(playlistDto, Playlist.class);
        if(playlistDto.getId() != null){
            playlist.setId(playlistDto.getId());
            playlist.setAuthor(playlistDto.getAuthor());
            playlist.setGenre(playlistDto.getGenre());
            playlist.setSubFee(playlistDto.getSubFee());
            playlist.setTitle(playlistDto.getTitle());
            playlist.setSongs(playlistDto.getSongs());
        }
        return playlist;
    }

    private Song convertSongToEntity(SongDto songDto) throws ParseException {
        Song song = modelMapper.map(songDto, Song.class);
        if(songDto.getId() != null){
            song.setId(songDto.getId());
            song.setAuthor(songDto.getAuthor());
            song.setName(songDto.getName());
            song.setDuration(songDto.getDuration());
            song.setPlaylistSet(songDto.getPlaylistSet());
        }
        return song;
    }
}



