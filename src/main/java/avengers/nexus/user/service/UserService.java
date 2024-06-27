package avengers.nexus.user.service;

import avengers.nexus.gauth.service.GauthService;
import avengers.nexus.github.domain.GithubUser;
import avengers.nexus.github.service.OauthService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.TokenMemoryRepository;
import avengers.nexus.user.repository.UserRepository;
import dev.yangsijun.gauth.core.user.GAuthUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenMemoryRepository tokenMemoryRepository;
    private final OauthService oauthService;
    private final GauthService gauthService;

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
    }

    public void registerUser(UserSignupDto userSignupDto) {
        User user = User.builder()
                .name(userSignupDto.getName())
                .profileImageUrl(userSignupDto.getProfileImageUrl())
                .build();
        userRepository.save(user);
    }
    public User loginWithGithub(String accessCode) {
        String token = oauthService.getAccessToken(accessCode);
        GithubUser githubUser = oauthService.getGithubUserByToken(token);
        User user = userRepository.findByGithubId(githubUser.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        saveGithubToken(user, token);
        return user;
    }
    public User loginWithGauth(String accessCode) {
        GAuthUser gauthUser = gauthService.getUserByAccessCode(accessCode);
        return userRepository.findByName(gauthUser.getName()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
    }

    private void saveGithubToken(User user, String githubToken) {
        tokenMemoryRepository.saveGithubToken(user, githubToken);
    }
}
