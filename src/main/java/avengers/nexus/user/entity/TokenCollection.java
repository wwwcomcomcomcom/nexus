package avengers.nexus.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenCollection {
    private String githubToken;
    private String gauthToken;
}
