package webprogramming.playlistapp.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import webprogramming.playlistapp.dtos.PlaylistDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "playlist")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property  = "title",
        scope     = Playlist.class)
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "playlist_id")
    private long id;

    @NotEmpty(message = "Playlist title is required")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author is required")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Genre is required")
    @Column(name = "genre")
    private String genre;

    @NotEmpty
    @Column(name = "subscription_fee")
    private String subFee;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> songs = new HashSet<>();

    @OneToMany(mappedBy = "playlist")
    Set<Subscription> subscriptions;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Playlist) {
            return ((Playlist) o).getTitle().equals(getTitle());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }

    public Playlist() {
    }

    public Playlist(long id, String title, String author,
                    String genre, String subFee, Set<Song> songs,
                    Set<Subscription> subscriptions){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.songs = songs;
        this.subFee = subFee;
        this.subscriptions = subscriptions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubFee() {
        return subFee;
    }

    public void setSubFee(String subFee) {
        this.subFee = subFee;
    }

    @JsonBackReference
    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}


