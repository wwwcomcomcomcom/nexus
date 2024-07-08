package avengers.nexus.user.controller;

import avengers.nexus.gauth.service.GauthService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import gauth.GAuthUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
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
        GAuthUserInfo gauthUser = gauthService.getUserInfoByCode(accessCode);
        //user profile is Nullable
        UserSignupDto user = new UserSignupDto(gauthUser.getName(),gauthUser.getProfileUrl());
        userService.registerUser(user);
        return ResponseEntity.ok("User signed up!");
    }
    @PostMapping("/login/github")
    public ResponseEntity<String> loginWithGithub(@RequestParam String accessCode, HttpServletRequest request) {
        User user = userService.loginWithGithub(accessCode);
        issueSession(user, request);
        return ResponseEntity.ok("User logged in!");
    }
    @PostMapping("/login/gauth")
    public ResponseEntity<String> loginWithGauth(@RequestParam String accessCode, HttpServletRequest request) {
        User user = userService.loginWithGauth(accessCode);
        issueSession(user, request);
        return ResponseEntity.ok("User logged in!");
    }
    private void issueSession(User user, HttpServletRequest request) {
        request.getSession().invalidate();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(86400);
    }
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("User logged out!");
    }
    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        return ResponseEntity.ok(user);
    }
}
