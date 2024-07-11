package avengers.nexus.authentication.service;

import avengers.nexus.user.entity.User;


public interface AuthenticateService {
    String getAccessToken(String authorizationCode);
}
