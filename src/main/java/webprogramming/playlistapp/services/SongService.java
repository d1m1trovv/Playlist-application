package webprogramming.playlistapp.services;

import webprogramming.playlistapp.entities.Song;

import java.util.List;

public interface SongService {
    List<Song> findAllSongs();
}
