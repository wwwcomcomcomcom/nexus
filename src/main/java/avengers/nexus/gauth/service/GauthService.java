package avengers.nexus.gauth.service;


import avengers.nexus.auth.service.AuthenticateService;
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
}
