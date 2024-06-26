package avengers.nexus.github.domain;

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
    private String avatarUrl;
}
