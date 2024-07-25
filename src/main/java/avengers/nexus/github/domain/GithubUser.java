package avengers.nexus.github.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class GithubUser {

    @Setter
    private String token;

    private Long id;
    private String login;
    private String email;
    @JsonProperty("avatar_url")
    private String avatarUrl;
}
