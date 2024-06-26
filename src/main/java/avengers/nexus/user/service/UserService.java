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
                .password(userSignupDto.getPassword())
                .build();
        userRepository.save(user);
    }
    public User login(String name, String password) {
        return userRepository.findByNameAndPassword(name, password).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed")
        );
    }
}