package webprogramming.playlistapp.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.dtos.UserDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.entities.User;
import webprogramming.playlistapp.services.PlaylistServiceImpl;
import webprogramming.playlistapp.services.SongServiceImpl;
import webprogramming.playlistapp.services.UserDetailsImpl;
import webprogramming.playlistapp.services.UserServiceImpl;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AdminController {

    private String keyString = "adkj@#$02#@adflkj)(*jlj@#$#@LKjasdjlkj<.,mo@#$@#kljlkdsu343";

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

    @PostMapping(value = "/login")
    public ResponseEntity<?> userLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roles, HttpStatus.OK);
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

    @PutMapping("/admin/playlists/{id}")
    public PlaylistDto updatePlaylist(@PathVariable("id") long id, @RequestBody PlaylistDto playlistDto) {
        try {
            Playlist tempPl = playlistService.convertPlaylistDtoToEntity(playlistDto);

            return playlistService.findPlaylistById(id)
                    .map(playlist -> {
                        playlist.setTitle(playlistDto.getTitle());
                        playlist.setAuthor(playlistDto.getAuthor());
                        playlist.setGenre(playlistDto.getGenre());
                        playlist.setSubFee(playlistDto.getSubFee());
                        try {
                            playlist.setSongs(playlistService.convertPlaylistDtoSetToEntity(playlistDto.getSongs()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        playlistService.updatePlaylist(playlist);
                        return playlistService.convertPlaylistToDto(playlist);
                    })
                    .orElseGet(() -> {
                        playlistDto.setId(id);
                        return playlistService.convertPlaylistToDto(playlistService.updatePlaylist(tempPl));
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

            Map<String,String> map = new HashMap<String,String>();
            map.put("id",String.valueOf(id));
            map.put("topSecret","Waffles are tasty");

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
            Song song = songService.convertSongDtoToEntity(songDto);
            Set<Playlist> playlistSet = song.getPlaylistSet();

            return playlistService.findPlaylistById(id)
                    .map(playlist -> {
                        Set<Song> songs = playlist.getSongs();
                        songs.add(song);
                        playlist.setSongs(songs);
                        playlistSet.add(playlist);
                        song.setPlaylistSet(playlistSet);
                        songService.updateSong(song);
                        return playlistService.convertPlaylistToDto(playlistService.updatePlaylist(playlist));
                    })
                    .orElseGet(() -> {
                        playlistService.findPlaylistById(id).get().setId(id);
                        return playlistService.convertPlaylistToDto(playlistService.updatePlaylist(playlistService.findPlaylistById(id).get()));
                    });
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/admin/deleteSongsFromPlaylist/{id}")
    public ResponseEntity<List<SongDto>> getSongsToDelete(@PathVariable long id){
        try {

           List<SongDto> songDtos = new ArrayList<>();

           for(Song song : playlistService.findPlaylistById(id).get().getSongs()){
               SongDto songDto = songService.convertSongToDto(song);
               songDtos.add(songDto);
           }

            if (songDtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(songDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/admin/deleteSongsFromPlaylist/{id}")
    public PlaylistDto deleteSongFromPlaylist(@PathVariable long id,
                                              @RequestBody SongDto songDto){
        try {
            Song song = songService.convertSongDtoToEntity(songDto);
            Set<Playlist> playlistSet = song.getPlaylistSet();

            return playlistService.findPlaylistById(id)
                    .map(playlist -> {
                        Set<Song> songs = playlist.getSongs();
                        songs.remove(song);
                        playlist.setSongs(songs);
                        playlistSet.remove(playlist);
                        song.setPlaylistSet(playlistSet);
                        songService.updateSong(song);
                        return playlistService.convertPlaylistToDto(playlistService.updatePlaylist(playlist));
                    })
                    .orElseGet(() -> {
                        playlistService.findPlaylistById(id).get().setId(id);
                        return playlistService.convertPlaylistToDto(playlistService.updatePlaylist(playlistService.findPlaylistById(id).get()));
                    });
        }catch (Exception e){
            return null;
        }
    }
    /**
     * Encrypts and encodes the Object and IV for url inclusion
     * @param
     * @return
     * @throws Exception
     */
    private String[] encryptObject(Object obj) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(stream);
        try {
            // Serialize the object
            out.writeObject(obj);
            byte[] serialized = stream.toByteArray();

            // Setup the cipher and Init Vector
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[cipher.getBlockSize()];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Hash the key with SHA-256 and trim the output to 128-bit for the key
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(keyString.getBytes());
            byte[] key = new byte[16];
            System.arraycopy(digest.digest(), 0, key, 0, key.length);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

            // encrypt
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            // Encrypt & Encode the input
            byte[] encrypted = cipher.doFinal(serialized);
            byte[] base64Encoded = Base64.encodeBase64(encrypted);
            String base64String = new String(base64Encoded);
            String urlEncodedData = URLEncoder.encode(base64String,"UTF-8");

            // Encode the Init Vector
            byte[] base64IV = Base64.encodeBase64(iv);
            String base64IVString = new String(base64IV);
            String urlEncodedIV = URLEncoder.encode(base64IVString, "UTF-8");

            return new String[] {urlEncodedData, urlEncodedIV};
        }finally {
            stream.close();
            out.close();
        }
    }

    /**
     * Decrypts the String and serializes the object
     * @param base64Data
     * @param base64IV
     * @return
     * @throws Exception
     */
    public Object decryptObject(String base64Data, String base64IV) throws Exception {
        // Decode the data
        byte[] encryptedData = Base64.decodeBase64(base64Data.getBytes());

        // Decode the Init Vector
        byte[] rawIV = Base64.decodeBase64(base64IV.getBytes());

        // Configure the Cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(rawIV);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(keyString.getBytes());
        byte[] key = new byte[16];
        System.arraycopy(digest.digest(), 0, key, 0, key.length);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // Decrypt the data..
        byte[] decrypted = cipher.doFinal(encryptedData);

        // Deserialize the object
        ByteArrayInputStream stream = new ByteArrayInputStream(decrypted);
        ObjectInput in = new ObjectInputStream(stream);
        Object obj = null;
        try {
            obj = in.readObject();
        }finally {
            stream.close();
            in.close();
        }
        return obj;
    }
}




