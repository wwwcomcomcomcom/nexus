package avengers.nexus.user.service;

import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository userRepository;
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
