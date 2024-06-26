package avengers.nexus.user.entity;

import avengers.nexus.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private String name;
    private String password;

    @Convert(converter = StringListConverter.class)
    private List<String> followers;
    @Convert(converter = StringListConverter.class)
    private List<String> followings;

    public static UserBuilder builder() {
        return new UserBuilder().followers(new ArrayList<>()).followings(new ArrayList<>());
    }
}
