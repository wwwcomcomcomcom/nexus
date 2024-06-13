package avengers.nexus.github.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OauthService {
    @Value("${github.client-id}")
    private String clientId;
    @Value("${github.client-secret}")
    private String clientSecret;
    private final RestTemplate restTemplate;
    public OauthService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public String getAccessToken(String authorizationCode) {
        String url = "https://github.com/login/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", authorizationCode);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<Map<String, String>>() {});

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = response.getBody();
            if(responseBody == null) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"github responded null");
            return responseBody.get("access_token");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Failed to retrieve access token");
        }
    }
}
