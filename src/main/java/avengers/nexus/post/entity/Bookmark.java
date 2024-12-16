package avengers.nexus.post.entity;

import avengers.nexus.Timestamped;
import avengers.nexus.user.entity.User;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("bookmarks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Bookmark extends Timestamped {
    @Id
    private String id;

    private User userId;
    private List<Post> postIds;
}
