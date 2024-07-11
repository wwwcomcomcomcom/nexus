package avengers.nexus.authentication.filter;

import avengers.nexus.authentication.jwt.JWTUtil;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import jakarta.servlet.FilterChain;
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

public class GauthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTUtil jwtUtil;
    private final UserService userService;

    //TODO:builder pattern
    public GauthAuthenticationFilter(String url, AuthenticationManager authManager, JWTUtil jwtUtil, UserService userService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        String accessCode = request.getParameter("accessCode");
        try {
            User user = userService.loginWithGauth(accessCode);
            return new UsernamePasswordAuthenticationToken(user, user.getId());
        }catch (ResponseStatusException e){
            throw new AuthenticationException("Authentication failed by " + e.getStatusCode() + e.getMessage()) {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        String jwt = jwtUtil.createJwt(user.getName(), user.getId(),86400000L);
        System.out.println("Authentication was success");
        response.addHeader("Authorization", "Bearer " + jwt);
    }
}
