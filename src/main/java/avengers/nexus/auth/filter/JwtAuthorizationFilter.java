package avengers.nexus.auth.filter;

import avengers.nexus.auth.dto.CustomUserDetails;
import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authorization.split(" ")[1];
        if(jwtUtil.isExpired(token)){
            filterChain.doFilter(request,response);
            return;
        }
        String username = jwtUtil.getUsername(token);
        Long userId = jwtUtil.getUserId(token);
        try {
            User user = userService.getUserById(userId);
            if (!Objects.equals(user.getName(), username)) return;
            CustomUserDetails userDetails = new CustomUserDetails(user);
            Authentication authToken = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (ResponseStatusException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getReason());
        }
        finally {
            filterChain.doFilter(request,response);
        }
    }
}
