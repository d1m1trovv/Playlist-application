package webprogramming.playlistapp.services;

import org.modelmapper.ModelMapper;
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


import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("playlistService")
public class PlaylistServiceImpl implements PlaylistService{


    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final SongServiceImpl songService;

    @Autowired
    public PlaylistServiceImpl(@Qualifier("playlistRepository") PlaylistRepository playlistRepository,
                               @Qualifier("userRepository") UserRepository userRepository,
                               @Qualifier("songRepository") SongRepository songRepository,
                               @Qualifier("modelMapper") ModelMapper modelMapper,
                               @Qualifier("songService") SongServiceImpl songService){
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
        this.songService = songService;
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
    public void addSong(long playlistId, Song songEnt) {
        Song song = new Song();
        song.setName(songEnt.getName());
        song.setAuthor(songEnt.getAuthor());
        song.setDuration(songEnt.getDuration());
        Set<Playlist> songPlaylists = song.getPlaylistSet();
        songPlaylists.add(playlistRepository.findById(playlistId).get());
        song.setPlaylistSet(songPlaylists);
        Set<Song> tempSongs = playlistRepository.findById(playlistId).get().getSongs();
        tempSongs.add(song);
        playlistRepository.findById(playlistId).get().setSongs(tempSongs);
        songRepository.save(song);
    }

    @Override
    public Set<Song> findAllPlaylistSongs(Long id) {
        return playlistRepository.findById(id).get().getSongs();
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
    public PlaylistDto convertPlaylistToDto(Playlist playlist) {
        PlaylistDto playlistDto = modelMapper.map(playlist, PlaylistDto.class);
        playlistDto.setId(playlist.getId());
        playlistDto.setAuthor(playlist.getAuthor());
        playlistDto.setGenre(playlist.getGenre());
        playlistDto.setSubFee(playlist.getSubFee());
        playlistDto.setTitle(playlist.getTitle());
        return playlistDto;
    }

    @Override
    public Playlist convertPlaylistDtoToEntity(PlaylistDto playlistDto) throws ParseException {
        Playlist playlist = modelMapper.map(playlistDto, Playlist.class);
        if(playlistDto.getId() != null){
            playlist.setId(playlistDto.getId());
            playlist.setAuthor(playlistDto.getAuthor());
            playlist.setGenre(playlistDto.getGenre());
            playlist.setSubFee(playlistDto.getSubFee());
            playlist.setTitle(playlistDto.getTitle());
            playlist.setSongs(convertPlaylistDtoSetToEntity(playlistDto.getSongs()));
        }
        return playlist;
    }

    @Override
    public Set<Song> convertPlaylistDtoSetToEntity(Set<SongDto> songsDto) throws ParseException {

        Set<Song> songSet = songsDto.stream()
                .map(songDto -> {
                    try {
                        return songService.convertSongDtoToEntity(songDto);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toSet());
        return songSet;
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

