package webprogramming.playlistapp.services;

import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {

    Playlist findPlaylistByTitle(String title);
    List<Playlist> findAll();
    Playlist createPlaylist(PlaylistDto playlistDto);
    Optional<Playlist> findById(long id);
    void addSong(long playlistId, SongDto songDto);
    List<Song> findAllPlaylistSongs(String plTitle);
    void deleteSong(String plTitle, int songID);
    boolean checkIfPlaylistExists(String title);
    boolean checkIfSongExists(String name, String title);
    Playlist updatePlaylist(Playlist playlist);
    void deletePlaylist(long id);
}
