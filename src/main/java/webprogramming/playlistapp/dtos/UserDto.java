package webprogramming.playlistapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webprogramming.playlistapp.validations.FieldMatch;
import webprogramming.playlistapp.validations.ValidEmail;
import webprogramming.playlistapp.validations.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch(first = "pass", second = "confirmPass", message = "Passwords must match")
public class UserDto {

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
    private String pass;

    @NotEmpty
    @NotNull
    private String confirmPass;
}

