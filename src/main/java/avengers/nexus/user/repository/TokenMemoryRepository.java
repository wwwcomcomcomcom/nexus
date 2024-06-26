package avengers.nexus.user.repository;

import avengers.nexus.user.entity.TokenCollection;
import avengers.nexus.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class TokenMemoryRepository {
    private final HashMap<User, TokenCollection> tokenMap = new HashMap<>();

    public void saveTokens(User user, String githubToken, String gauthToken) {
        tokenMap.put(user, new TokenCollection(githubToken, gauthToken));
    }

    public void saveTokens(User user, TokenCollection token) {
        tokenMap.put(user, token);
    }

    public TokenCollection getTokens(User user) {
        return tokenMap.get(user);
    }

    public void deleteTokens(User user) {
        tokenMap.remove(user);
    }

    public String getGithubToken(User user) {
        return tokenMap.get(user).getGithubToken();
    }

    public String getGauthToken(User user) {
        return tokenMap.get(user).getGauthToken();
    }
}
