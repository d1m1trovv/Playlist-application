package webprogramming.playlistapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Song;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

    @NotNull
    @NotEmpty
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String author;

    @NotEmpty
    @NotNull
    private String duration;

    @NotEmpty
    @NotNull
    private Set<Playlist> playlistSet;
}
