package avengers.nexus.user.entity;

import avengers.nexus.utils.LongListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //gauth name
    private String name;
    //basically gauth profile image url  @Nullable
    private String profileImageUrl;
    //Not a nickname or a login but the github pk id   @Nullable
    private Long githubId;

    @Convert(converter = LongListConverter.class)
    private List<Long> followers = List.of();
    @Convert(converter = LongListConverter.class)
    private List<Long> followings = List.of();
}
