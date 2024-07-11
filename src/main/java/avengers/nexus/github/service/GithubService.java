package avengers.nexus.github.service;

import avengers.nexus.authentication.service.AuthenticateService;
import avengers.nexus.github.domain.GithubUser;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GithubService implements AuthenticateService {
    @Value("${github.client-id}")
    private String clientId;
    @Value("${github.client-secret}")
    private String clientSecret;
    private final RestTemplate restTemplate;

    public String getAccessToken(String authorizationCode) {
        String url = "https://github.com/login/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", authorizationCode);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = response.getBody();
            if(responseBody == null) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"github responded null");
            return responseBody.get("access_token");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Failed to retrieve access token");
        }
    }
    public GithubUser getGithubUserByToken(String token) {
        String url = "https://api.github.com/user";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + token);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<GithubUser> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, GithubUser.class);
        } catch (HttpStatusCodeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to retrieve user info from github" + e.getMessage());
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to retrieve user info from github");
        }
        GithubUser githubUser = Optional.ofNullable(response.getBody()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to retrieve user info from github"));
        githubUser.setToken(token);
        return githubUser;
    }

    public GithubUser getGithubUserByAccessCode(String accessCode){
        String token = getAccessToken(accessCode);
        return getGithubUserByToken(token);
    }
}
