package avengers.nexus.gauth.controller;

import avengers.nexus.gauth.service.GauthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/gauth")
public class GauthController {
    private final GauthService gauthService;
    GauthController(GauthService gauthService) {
        this.gauthService = gauthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAndRegisterTokenAtSession(String authorizationCode, HttpServletRequest request) {
        request.getSession().setAttribute("gauthToken", gauthService.getAccessToken(authorizationCode));
        return ResponseEntity.ok("Logged in with Gauth account!");
    }
}
