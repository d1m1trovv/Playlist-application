package webprogramming.playlistapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webprogramming.playlistapp.validations.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    @ValidEmail
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String username;
}
