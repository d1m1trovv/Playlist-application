package webprogramming.playlistapp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "playlist_id")
    private int id;

    @NotEmpty(message = "Playlist title is required")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author is required")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Genre is required")
    @Column(name = "genre")
    private String genre;

    @NotNull
    @Column(name = "subscription_fee")
    private double subFee;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> songs;

    @OneToMany(mappedBy = "playlist")
    Set<Subscription> subscriptions;
}


