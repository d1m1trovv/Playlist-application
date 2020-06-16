package webprogramming.playlistapp.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webprogramming.playlistapp.entities.Song;


import java.util.List;

@Repository("songRepository")
public interface SongRepository extends JpaRepository<Song, Integer> {

    @Override
    List<Song> findAll();
    Song findSongById(long id);
    void deleteById(long id);
    List<Song> findAllByPlaylistId(long id);

}
