package avengers.nexus.user.repository;

import avengers.nexus.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class TokenMemoryRepository {
    private final HashMap<User, String> githubTokenMap = new HashMap<>();
    private final HashMap<User, String> gauthTokenMap = new HashMap<>();

    public void saveGithubToken(User user, String githubToken) {
        githubTokenMap.put(user, githubToken);
    }

    public void saveGauthToken(User user, String gauthToken) {
        gauthTokenMap.put(user, gauthToken);
    }

    public Optional<String> getGithubToken(User user) {
        return Optional.ofNullable(githubTokenMap.get(user));
    }

    public Optional<String> getGauthToken(User user) {
        return Optional.ofNullable(gauthTokenMap.get(user));
    }
}
