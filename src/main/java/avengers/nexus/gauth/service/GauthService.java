package avengers.nexus.gauth.service;


import avengers.nexus.authentication.service.AuthenticateService;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import gauth.GAuth;
import gauth.GAuthToken;
import gauth.GAuthUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GauthService implements AuthenticateService {
    @Value("${gauth.client-id}")
    private String clientId;
    @Value("${gauth.client-secret}")
    private String clientSecret;

    private final GAuth gAuth;
    private final UserService userService;

    public String getAccessToken(String authorizationCode) {
        GAuthToken token = gAuth.generateToken(
                authorizationCode,
                clientId,
                clientSecret,
                "http://localhost:5173"
        );
        return token.getAccessToken();
    }
    public GAuthUserInfo getUserInfoByCode(String accessCode) {
        String accessToken = getAccessToken(accessCode);
        return getUserInfo(accessToken);
    }
    public GAuthUserInfo getUserInfo(String accessToken) {
        return gAuth.getUserInfo(accessToken);
    }

    @Override
    public User findUserByAccessCode(String code) {
        GAuthUserInfo userInfo = getUserInfoByCode(code);
        return userService.getUserByName(userInfo.getName());
    }
}
