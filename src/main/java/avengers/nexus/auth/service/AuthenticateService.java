package avengers.nexus.auth.service;


public interface AuthenticateService {
    String getAccessToken(String authorizationCode);
}
