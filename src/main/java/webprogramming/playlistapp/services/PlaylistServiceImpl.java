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
import java.util.Optional;
import java.util.Set;

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
    public void createPlaylist(PlaylistDto playlistDto) {
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistDto.getTitle());
        playlist.setAuthor(playlistDto.getAuthor());
        playlist.setGenre(playlistDto.getGenre());
        playlist.setSubFee(playlistDto.getSubFee());
        playlistRepository.save(playlist);
    }

    @Override
    public void addSong(long playlistId, SongDto songDto) {
        Song song = new Song();
        song.setName(songDto.getName());
        song.setAuthor(songDto.getAuthor());
        song.setDuration(songDto.getDuration());
        song.setPlaylist(playlistRepository.findById(playlistId).get());
        Set<Song> tempSongs = playlistRepository.findById(playlistId).get().getSongs();
        tempSongs.add(song);
        playlistRepository.findById(playlistId).get().setSongs(tempSongs);
        songRepository.save(song);
    }

    @Override
    public List<Song> findAllPlaylistSongs(Long id) {
        return songRepository.findAllByPlaylistId(id);
    }

    @Override
    public Optional<Playlist> findPlaylistById(long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public boolean checkIfPlaylistExists(String title) {
        Playlist playlist = playlistRepository.findPlaylistByTitle(title);

        return playlist != null;
    }

    @Override
    public Playlist updatePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public void deletePlaylist(long id) {
        playlistRepository.deleteById(id);
    }
}

