package avengers.nexus.gauth.controller;

import avengers.nexus.gauth.service.GauthService;
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
    public ResponseEntity<?> getAccessToken(String authorizationCode) {

        return ResponseEntity.ok(gauthService.getAccessToken(authorizationCode));
    }
}
