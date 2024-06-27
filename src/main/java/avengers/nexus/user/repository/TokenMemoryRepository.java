package avengers.nexus.user.repository;

import avengers.nexus.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class TokenMemoryRepository {
    private final HashMap<User, String> githubTokenMap = new HashMap<>();

    public void saveGithubToken(User user, String githubToken) {
        githubTokenMap.put(user, githubToken);
    }

    public Optional<String> getGithubToken(User user) {
        return Optional.ofNullable(githubTokenMap.get(user));
    }
}
