package webprogramming.playlistapp.services;

import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;

import java.util.List;

public interface PlaylistService {

    Playlist findPlaylistByTitle(String title);
    List<Playlist> findAll();
    Playlist createPlaylist(PlaylistDto playlistDto);
    Playlist findById(int id);
    void addSong(int playlistId, SongDto songDto);
    List<Song> findAllPlaylistSongs(String plTitle);
    void deleteSong(String plTitle, int songID);
    boolean checkIfPlaylistExists(String title);
    boolean checkIfSongExists(String name, String title);
}
