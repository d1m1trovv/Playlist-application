package webprogramming.playlistapp.services;

import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> findAllSongs();
    void createSong(SongDto songDto);
    Optional<Song> findSongById(long id);
    Song updateSong(Song song);
    SongDto convertSongToDto(Song song);
    Song convertSongDtoToEntity(SongDto songDto) throws ParseException;
}
