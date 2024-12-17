package avengers.nexus.post.domain;

import avengers.nexus.Timestamped;
import avengers.nexus.user.entity.User;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Reply extends Timestamped {
    @Id
    private String id;

    private String content;
    private User author;
    private String postId;
    
}
