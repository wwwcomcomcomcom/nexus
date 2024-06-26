package avengers.nexus.user.service;

import avengers.nexus.github.domain.GithubUser;
import avengers.nexus.github.service.OauthService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OauthService oauthService;

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
                .build();
        userRepository.save(user);
    }
    public User login(String name, String password) {
        //TODO: separate login logic github vs gauth
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public User loginWithGithub(String token) {
        GithubUser user = oauthService.getGithubUserByToken(token);
        return userRepository.findByGithubId(user.getId()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
    }
}
