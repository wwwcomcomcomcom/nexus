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
            System.out.println(request.getRequestURI());
            System.out.println(authorization);
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
        User user = userService.getUserById(userId);
        if(!Objects.equals(user.getName(), username)) return;
        System.out.println("complete");
        CustomUserDetails userDetails = new CustomUserDetails(user);
        Authentication authToken = new UsernamePasswordAuthenticationToken(user,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
