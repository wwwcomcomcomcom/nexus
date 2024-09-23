package avengers.nexus.user.controller;

import avengers.nexus.gauth.service.GauthService;
import avengers.nexus.github.domain.GithubUser;
import avengers.nexus.github.service.GithubService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import gauth.GAuthUserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final GauthService gauthService;
    private final GithubService githubService;
    @PostMapping("/signup/gauth")
    //signup with gauth
    public ResponseEntity<String> signup(@RequestParam String accessCode) {
        GAuthUserInfo gauthUser = gauthService.getUserInfoByCode(accessCode);
        //user profile is Nullable
        UserSignupDto user = UserSignupDto.builder()
                .name(gauthUser.getName())
                .profileImageUrl(gauthUser.getProfileUrl()).build();
        userService.registerUser(user);
        return ResponseEntity.ok("User signed up!");
    }
    @PostMapping("/signup/github")
    public ResponseEntity<String> signupWithGithub(@RequestParam String accessCode){
        GithubUser githubUser = githubService.getGithubUserByAccessCode(accessCode);
        UserSignupDto user = UserSignupDto.builder()
                .name(githubUser.getLogin())
                .profileImageUrl(githubUser.getAvatarUrl())
                .githubId(githubUser.getId()).build();
        userService.registerUser(user);
        return ResponseEntity.ok("User signed up!");
    }
    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        return ResponseEntity.ok(user);
    }
}
