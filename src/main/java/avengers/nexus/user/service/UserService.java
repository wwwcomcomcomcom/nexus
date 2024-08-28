package avengers.nexus.user.service;

import avengers.nexus.gauth.service.GauthService;
import avengers.nexus.github.domain.GithubUser;
import avengers.nexus.github.service.GithubService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.TokenMemoryRepository;
import avengers.nexus.user.repository.UserRepository;
import gauth.GAuthUserInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenMemoryRepository tokenMemoryRepository;
    private final GithubService githubService;
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

    public User getUserByGithubId(Long githubId){
        return userRepository.findByGithubId(githubId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
    }

    public void registerUser(UserSignupDto userSignupDto) {
        User user = User.builder()
                .name(userSignupDto.getName())
                .profileImageUrl(userSignupDto.getProfileImageUrl())
                .githubId(userSignupDto.getGithubId())
                .followers(List.of())
                .followings(List.of())
                .build();
        userRepository.save(user);
    }
    public User loginWithGithub(String accessCode) {
        String token = githubService.getAccessToken(accessCode);
        GithubUser githubUser = githubService.getGithubUserByToken(token);
        User user = userRepository.findByGithubId(githubUser.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        saveGithubToken(user, token);
        return user;
    }
    public User loginWithGauth(String accessCode) {
        GAuthUserInfo gauthUser = gauthService.getUserInfoByCode(accessCode);
        return userRepository.findByName(gauthUser.getName()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
    }

    private void saveGithubToken(User user, String githubToken) {
        tokenMemoryRepository.saveGithubToken(user, githubToken);
    }
}
