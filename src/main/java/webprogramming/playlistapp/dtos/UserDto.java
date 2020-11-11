package webprogramming.playlistapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webprogramming.playlistapp.entities.Playlist;
import webprogramming.playlistapp.entities.Role;
import webprogramming.playlistapp.validations.FieldMatch;
import webprogramming.playlistapp.validations.ValidEmail;
import webprogramming.playlistapp.validations.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "pass", second = "confirmPass", message = "Passwords must match")
public class UserDto {

    @NotNull
    @NotEmpty
    private Long id;

    @ValidEmail
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String username;

    @ValidPassword
    @NotEmpty
    @NotNull
    private String password;

    @ValidPassword
    @NotEmpty
    @NotNull
    private String confirmPassword;

    @NotEmpty
    @NotNull
    private Set<Playlist> playlists;
}

