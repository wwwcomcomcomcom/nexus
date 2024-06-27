package avengers.nexus.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupDto {
    private String name;
    private String profileImageUrl;
}
