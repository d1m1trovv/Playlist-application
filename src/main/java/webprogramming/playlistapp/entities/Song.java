package webprogramming.playlistapp.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import webprogramming.playlistapp.dtos.PlaylistDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "song")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "name",
        scope     = Song.class)
public class Song {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private long id;

    @NotEmpty(message = "Song name is required")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Song author is required")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Song duration is required")
    @Column(name = "duration")
    private String duration;

    @ManyToMany(mappedBy = "songs", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Playlist> playlistSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Song) {
            return ((Song) o).getName().equals(getName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public Song() {
    }

    public Song(long id, String name, String author, String duration, Set<Playlist> playlists){
        this.id = id;
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.playlistSet = playlists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Set<Playlist> getPlaylistSet() {
        return playlistSet;
    }

    public void setPlaylistSet(Set<Playlist> playlistSet) {
        this.playlistSet = playlistSet;
    }
}

