package avengers.nexus.post.domain;

import avengers.nexus.user.entity.User;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Bookmark {
    @Id
    private String id;

    private User userId;
    private List<Post> postIds;
}
