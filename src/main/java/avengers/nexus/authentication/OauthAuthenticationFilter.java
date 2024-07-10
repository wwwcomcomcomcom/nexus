package avengers.nexus.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class OauthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenProvider tokenProvider;
    private final ExternalAuthenticationService externalAuthService;

    public OauthAuthenticationFilter(String url, AuthenticationManager authManager,
                                   JwtTokenProvider tokenProvider,
                                   ExternalAuthenticationService externalAuthService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.tokenProvider = tokenProvider;
        this.externalAuthService = externalAuthService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {
        String token = request.getReader().lines().reduce("", String::concat);

        boolean isAuthenticated = externalAuthService.authenticate(token);

        if (isAuthenticated) {
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(token, token)
            );
        } else {
            throw new AuthenticationException("Authentication failed") {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        String jwt = tokenProvider.createToken(authResult);
        response.addHeader("Authorization", "Bearer " + jwt);
    }
}
