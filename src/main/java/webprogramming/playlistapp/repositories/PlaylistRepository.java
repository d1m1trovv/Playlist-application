package webprogramming.playlistapp.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webprogramming.playlistapp.entities.Playlist;

import java.util.List;

@Repository("playlistRepository")
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Override
    List<Playlist> findAll();
    Playlist findPlaylistByTitle(String title);
    Playlist findPlaylistById(int id);
}
