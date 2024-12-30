package avengers.nexus.auth.jwt;

import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey secretKey;
    private final UserRepository userRepository;

    public JWTUtil(@Value("${jwt.secret}")String secret, UserRepository userRepository) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.userRepository = userRepository;
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }
    public Long getUserId(String token){
        try{
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", Long.class);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"토큰이 유효하지 않습니다. " + e.getMessage());
        }
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String username,Long userId, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public User getUserByAuthHeader(String authHeader) {
        try{
        String token = (String) authHeader.replace("Bearer ", "");
        Long userId = getUserId(token);
        return userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED,"사용자를 찾을 수 없습니다."));
        }catch (NullPointerException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"토큰이 없습니다.");
        }
    }
}
