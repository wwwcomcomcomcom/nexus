package avengers.nexus.authentication.service;


public interface AuthenticateService {
    String getAccessToken(String authorizationCode);
}
