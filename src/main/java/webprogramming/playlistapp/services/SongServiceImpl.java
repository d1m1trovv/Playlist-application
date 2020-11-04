package webprogramming.playlistapp.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.repositories.SongRepository;

import java.util.List;

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
}
