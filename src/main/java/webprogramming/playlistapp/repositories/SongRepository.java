package webprogramming.playlistapp.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import webprogramming.playlistapp.entities.Song;


import java.util.List;

@Repository("songRepository")
public interface SongRepository extends JpaRepository<Song, Long> {

    @Override
    List<Song> findAll();
    void deleteById(long id);


}
