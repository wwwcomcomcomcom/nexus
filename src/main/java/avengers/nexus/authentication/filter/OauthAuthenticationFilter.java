package avengers.nexus.authentication.filter;

import avengers.nexus.authentication.jwt.JWTUtil;
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

    private final JWTUtil jwtUtil;
    private final ExternalAuthenticationService externalAuthService;

    public OauthAuthenticationFilter(String url, AuthenticationManager authManager,
                                   JWTUtil jwtUtil,
                                   ExternalAuthenticationService externalAuthService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.jwtUtil = jwtUtil;
        this.externalAuthService = externalAuthService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {
        String accessCode = request.getReader().lines().reduce("", String::concat);

        boolean isAuthenticated = externalAuthService.authenticate(accessCode);

        if (isAuthenticated) {
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(accessCode, accessCode)
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
        String jwt = jwtUtil.createJwt(authResult);
        response.addHeader("Authorization", "Bearer " + jwt);
    }
}
