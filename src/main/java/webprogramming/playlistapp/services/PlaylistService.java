package webprogramming.playlistapp.services;

import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.PlaylistDto;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlaylistService {

    Playlist findPlaylistByTitle(String title);
    List<Playlist> findAll();
    void createPlaylist(PlaylistDto playlistDto);
    Optional<Playlist> findPlaylistById(long id);
    void addSong(long playlistId, Song songEnt);
    Set<Song> findAllPlaylistSongs(Long id);
    boolean checkIfPlaylistExists(String title);
    Playlist updatePlaylist(Playlist playlist);
    void deletePlaylist(long id);
    PlaylistDto convertPlaylistToDto(Playlist playlist);
    Playlist convertPlaylistDtoToEntity(PlaylistDto playlistDto) throws ParseException;
    Set<Song> convertPlaylistDtoSetToEntity(Set<SongDto> songDtoSet) throws ParseException;

}
