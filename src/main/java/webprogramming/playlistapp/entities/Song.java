package webprogramming.playlistapp.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "song")
public class Song {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private int id;

    @NotEmpty(message = "Song name is required")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Song author is required")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Song duration is required")
    @Column(name = "duration")
    private String duration;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    Playlist playlist;


}

