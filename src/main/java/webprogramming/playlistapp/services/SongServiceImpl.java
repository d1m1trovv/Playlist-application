package webprogramming.playlistapp.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.repositories.SongRepository;

import java.util.List;
import java.util.Optional;

@Service("songService")
public class SongServiceImpl implements SongService{

    private final SongRepository songRepository;

    public SongServiceImpl(@Qualifier("songRepository") SongRepository songRepository){
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> findAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public void createSong(SongDto songDto) {
        Song song = new Song();
        song.setName(songDto.getName());
        song.setAuthor(songDto.getAuthor());
        song.setDuration(songDto.getDuration());
        songRepository.save(song);
    }

    @Override
    public Optional<Song> findSongById(long id) {
        return songRepository.findById(id);
    }

    @Override
    public Song updateSong(Song song) {
        return songRepository.save(song);
    }
}
