package webprogramming.playlistapp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import webprogramming.playlistapp.dtos.SongDto;
import webprogramming.playlistapp.entities.Song;
import webprogramming.playlistapp.repositories.SongRepository;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service("songService")
public class SongServiceImpl implements SongService{

    private final SongRepository songRepository;
    private final ModelMapper modelMapper;

    public SongServiceImpl(@Qualifier("songRepository") SongRepository songRepository,
                           @Qualifier("modelMapper") ModelMapper modelMapper){
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
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
    public Song convertSongDtoToEntity(SongDto songDto) throws ParseException {
        Song song = modelMapper.map(songDto, Song.class);
        if(songDto.getId() != null){
            song.setId(songDto.getId());
            song.setAuthor(songDto.getAuthor());
            song.setName(songDto.getName());
            song.setDuration(songDto.getDuration());
            song.setPlaylistSet(songDto.getPlaylistSet());
        }
        return song;
    }

    @Override
    public SongDto convertSongToDto(Song song) {
        SongDto songDto = modelMapper.map(song, SongDto.class);
        songDto.setId(song.getId());
        songDto.setAuthor(song.getAuthor());
        songDto.setName(song.getName());
        songDto.setDuration(song.getDuration());
        songDto.setPlaylistSet(song.getPlaylistSet());
        return songDto;
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
