package avengers.nexus.gauth.service;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.userinfo.DefaultGAuthUserService;
import dev.yangsijun.gauth.userinfo.GAuthAuthorizationRequest;
import dev.yangsijun.gauth.userinfo.GAuthUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GauthService {
    @Value("${gauth.security.client-id}")
    private String clientId;
    @Value("${gauth.security.client-secret}")
    private String clientSecret;

    private final GAuthRegistration defaultRegistration = new GAuthRegistration(clientId, clientSecret,"https://localhost:8080/gauth/login");

    private final RestTemplate restTemplate;

    private GAuthUserService<GAuthAuthorizationRequest, GAuthUser> defaultUserService = new DefaultGAuthUserService();

    public GauthService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String getAccessToken(String authorizationCode) {
        String url = "https://server.gauth.co.kr/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("clientId", clientId);
        body.put("clientSecret", clientSecret);
        body.put("code", authorizationCode);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<Map<String, String>>() {});
        try{
            if(response.getStatusCode() != HttpStatus.OK) throw new Exception();
            Map<String, String> responseBody = response.getBody();
            if(responseBody == null) throw new Exception();
            return Optional.ofNullable(responseBody.get("accessToken")).orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Failed to retrieve access token from gauth");
        }
    }

    public GAuthUser getUserByAccessCode(String accessCode) throws GAuthAuthenticationException {
        GAuthAuthorizationRequest userRequest = new GAuthAuthorizationRequest(accessCode, defaultRegistration);
        return defaultUserService.loadUser(userRequest);
    }
}
