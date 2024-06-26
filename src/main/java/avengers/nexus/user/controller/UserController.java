package avengers.nexus.user.controller;

import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {
    private final UserService userService;
    UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public String signup(@RequestBody UserSignupDto user) {
        userService.registerUser(user);
        return "User signed up!";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserSignupDto user, HttpServletRequest request) {
        userService.login(user.getName(), user.getPassword());
        return ResponseEntity.ok("User logged in!");
    }
}
