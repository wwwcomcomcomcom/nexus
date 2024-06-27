package avengers.nexus.user.controller;

import avengers.nexus.gauth.service.GauthService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.GAuthUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    //signup with gauth
    public ResponseEntity<String> signup(@RequestParam String accessCode) {
        GAuthUser gauthUser;
        try{
            gauthUser = gauthService.getUserByAccessCode(accessCode);
        } catch (GAuthAuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to retrieve user info from gauth" + e.getMessage());
        }
        UserSignupDto user = new UserSignupDto(gauthUser.getName(),gauthUser.getAttribute("profileUrl"));
        userService.registerUser(user);
        return ResponseEntity.ok("User signed up!");
    }
    @PostMapping("/login/github")
    public ResponseEntity<String> loginWithGithub(@RequestParam String accessCode, HttpServletRequest request) {
        User user = userService.loginWithGithub(accessCode);
        request.getSession().invalidate();
        request.getSession().setAttribute("user", user);
        return ResponseEntity.ok("User logged in!");
    }
    @PostMapping("/login/gauth")
    public ResponseEntity<String> loginWithGauth(@RequestParam String accessCode, HttpServletRequest request) {
        User user = userService.loginWithGauth(accessCode);
        request.getSession().invalidate();
        request.getSession().setAttribute("user", user);
        return ResponseEntity.ok("User logged in!");
    }
    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        return ResponseEntity.ok(user);
    }
}
