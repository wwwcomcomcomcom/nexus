package avengers.nexus.user.controller;

import avengers.nexus.user.dto.UserSignupDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {
    @PostMapping("/signup")
    public String signup(@RequestBody UserSignupDto user) {
        //TODO: Implement user signup
        return "User signed up!";
    }
}
