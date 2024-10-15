package avengers.nexus.user.controller;

import avengers.nexus.gauth.service.GauthService;
import avengers.nexus.github.domain.GithubUser;
import avengers.nexus.github.service.GithubService;
import avengers.nexus.user.dto.UserSignupDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import gauth.GAuthUserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final GauthService gauthService;
    private final GithubService githubService;
    @PostMapping("/signup/gauth")
    @Operation(summary = "guath 회원가입", description = "guath AccessCode로 회원가입을 진행합니다.")
    @Parameter(name = "accessCode",description="gauth에서 받은 AccessCode",required = true)
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
    @Operation(summary = "github 회원가입", description = "github AccessCode로 회원가입을 진행합니다.")
    @Parameter(name = "accessCode",description="github에서 받은 AccessCode",required = true)
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
    @Operation(summary = "내 정보 조회", description = "마이페이지용 내 정보를 반환합니다.")
    public ResponseEntity<User> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        return ResponseEntity.ok(user);
    }
}
