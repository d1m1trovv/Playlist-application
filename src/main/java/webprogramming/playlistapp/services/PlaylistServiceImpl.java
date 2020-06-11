package webprogramming.playlistapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.repositories.PlaylistRepository;
import webprogramming.playlistapp.repositories.SongRepository;
import webprogramming.playlistapp.repositories.UserRepository;


import java.util.List;

@Service("playlistService")
public class PlaylistServiceImpl implements PlaylistService{


    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Autowired
    public PlaylistServiceImpl(@Qualifier("playlistRepository") PlaylistRepository playlistRepository,
                               @Qualifier("userRepository") UserRepository userRepository,
                               @Qualifier("songRepository") SongRepository songRepository){
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Playlist findPlaylistByTitle(String title) {
        return playlistRepository.findPlaylistByTitle(title);
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }


    @Override
    public Playlist createPlaylist(PlaylistDto playlistDto) {
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistDto.getTitle());
        playlist.setAuthor(playlistDto.getAuthor());
        playlist.setGenre(playlistDto.getGenre());
        playlist.setSubFee(playlistDto.getSubFee());
        playlistRepository.save(playlist);
        return playlist;
    }

    @Override
    public void addSong(int playlistId, SongDto songDto) {
        Song song = new Song();
        song.setName(songDto.getName());
        song.setAuthor(songDto.getAuthor());
        song.setDuration(songDto.getDuration());
        song.setPlaylist(playlistRepository.findPlaylistById(playlistId));
        songRepository.save(song);
    }

    @Override
    public void deleteSong(String plTitle, int songID) {
        List<Song> songs = findAllPlaylistSongs(plTitle);
        for(Song song : songs){
            if(song.getId() == songID){
                songRepository.deleteById(song.getId());
            }
        }
    }

    @Override
    public List<Song> findAllPlaylistSongs(String plTitle) {
        return songRepository.findAllByPlaylistId(playlistRepository.findPlaylistByTitle(plTitle).getId());
    }

    @Override
    public Playlist findById(int id) {
        return playlistRepository.findPlaylistById(id);
    }

    @Override
    public boolean checkIfPlaylistExists(String title) {
        Playlist playlist = playlistRepository.findPlaylistByTitle(title);

        return playlist != null;
    }

    @Override
    public boolean checkIfSongExists(String name, String title) {
        List<Song> songs = findAllPlaylistSongs(title);
        for(Song song : songs){
            if(song.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}

