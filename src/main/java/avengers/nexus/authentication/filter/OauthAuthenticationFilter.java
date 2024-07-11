package avengers.nexus.authentication.filter;

import avengers.nexus.authentication.jwt.JWTUtil;
import avengers.nexus.authentication.service.AuthenticateService;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
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
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class OauthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTUtil jwtUtil;
    private final AuthenticateService authService;

    //TODO:builder pattern
    public OauthAuthenticationFilter(String url, AuthenticationManager authManager, JWTUtil jwtUtil, AuthenticateService authService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {
        String accessCode = request.getReader().lines().reduce("", String::concat);
        try {
            User user = authService.findUserByAccessCode(accessCode);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(user, user.getId())
            );
        }catch (ResponseStatusException e){
            throw new AuthenticationException("Authentication failed by " + e.getStatusCode().toString() + e.getMessage()) {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        String jwt = jwtUtil.createJwt(user.getName(), user.getId(),86400000L);
        response.addHeader("Authorization", "Bearer " + jwt);
    }
}
