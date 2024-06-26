package avengers.nexus.user.controller;

import avengers.nexus.gauth.service.GauthService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.service.UserService;
import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.GAuthUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("/user")
public class UserController {
    private final UserService userService;
    private final GauthService gauthService;
    UserController(UserService userService, GauthService gauthService) {
        this.userService = userService;
        this.gauthService = gauthService;
    }
    @PostMapping("/signup")
    public String signup(@RequestParam String accessCode) {
        GAuthUser gauthUser;
        try{
            gauthUser = gauthService.getUserByAccessCode(accessCode);
        } catch (GAuthAuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to retrieve user info from gauth" + e.getMessage());
        }
        UserSignupDto user = new UserSignupDto(gauthUser.getName());
        userService.registerUser(user);
        return "User signed up!";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserSignupDto user, HttpServletRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
