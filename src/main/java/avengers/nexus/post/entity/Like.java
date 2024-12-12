package avengers.nexus.post.entity;


import avengers.nexus.user.entity.User;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Like {
    @Id
    private Long id;

    private User user;
    private Object target;

    public Like(User user, Object target) {
        this.user = user;
        this.target = target;
    }

}
