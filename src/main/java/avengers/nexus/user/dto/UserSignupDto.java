package avengers.nexus.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class UserSignupDto {
    private String id;
    private String name;
    private String profileImageUrl;
    private Long githubId;
}